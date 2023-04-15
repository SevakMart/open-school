import { useState, useMemo } from 'react';
import { useSelector } from 'react-redux';
import Loader from '../../../component/Loader/Loader';
import { RootState } from '../../../redux/Store';
import fetchData from '../../../services/fetchData';
import { CourseDescriptionType } from '../../../types/CourseTypes';
import './DiscussionForum.scss';
import { Question } from './interfaces/interfaces';
import AskQuestionPopup from './subcomponents/askQuestionPopup/AskQuestionPopup';
import QuestionItem from './subcomponents/QuestionItem/QuestionItem';

function DiscussionForum({ userInfo }:{userInfo:object}): JSX.Element {
  // save typed question
  const [value, setValue] = useState<string>('');
  const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    if (event.target.value.length > 500) event.preventDefault();
    else setValue(event.target.value);
  };

  // get course informatiom
  const courseDescriptionState = useSelector<RootState>((state) => state.courseDescriptionRequest) as { entity: CourseDescriptionType, isLoading: boolean, errorMessage: string };
  const { entity } = courseDescriptionState;

  // Loading
  const [isLoading, setIsLoading] = useState(false);

  // Popup
  const [isOpen, setPopupState] = useState<boolean>(false);

  const onOpen = () => {
    setPopupState(true);
  };

  const onClose = () => {
    setPopupState(false);
    setValue('');
  };

  // get id and token of user
  const idAndToken = useMemo(() => ({
    token: (userInfo as any).token,
    id: (userInfo as any).id,
  }), []);

  // forming an object with typed question and id from server for it
  const [questionsWithId, setQuestionsWithId] = useState<Question[]>([]);

  // add question to the addToQuestionObjArr and post it
  const addQuestion = async (val: string): Promise<void> => {
    if (!val) return;
    const body = {
      text: val,
    };
    try {
      setIsLoading(true); // set isLoading to true
      onClose();
      const response = await fetchData.post(`courses/enrolled/${entity.enrolledCourseId}/peers-questions`, body, {}, idAndToken.token);
      const responseJSON = await response.json();
      const { id, createdDate } = responseJSON;
      const newQuestion: Question = {
        id,
        text: val,
        createdDate,
      };

      setQuestionsWithId((prevQuestion) => [...prevQuestion, newQuestion]);
      setIsLoading(false); // set isLoading to false
      return responseJSON;
    } catch (error) {
      setIsLoading(false); // set isLoading to false in case of error
      throw new Error('Something went wrong');
    }
  };

  // edit question in server
  const editQuestionInServer = async (id: string, val: string): Promise<void> => {
    const body = {
      text: val,
    };
    try {
      const response = await fetchData.put(`courses/enrolled/${entity.enrolledCourseId}/peers-questions/${id}`, body, {}, idAndToken.token);
      const responseJSON = await response.json();
      return responseJSON;
    } catch (error) {
      throw new Error('Something went wrong');
    }
  };

  // edit text in UI
  const questionChanged = (id: string, newText: string): void => {
    setQuestionsWithId((prevQuestions) => prevQuestions.map((q) => (q.id === id ? { ...q, text: newText } : q)));
    editQuestionInServer(id, newText);
  };

  // removes question
  const removeQuestionInServer = async (id: string): Promise<void> => {
    try {
      const response = await fetchData.delete(`courses/enrolled/${entity.enrolledCourseId}/peers-questions/${id}`, {}, idAndToken.token);
      const responseJSON = await response.json();
      return responseJSON;
    } catch (error) {
      throw new Error('Something went wrong');
    }
  };

  const removeQuestion = (id: string):void => {
    setQuestionsWithId((prevQuestions) => prevQuestions.filter((q) => q.id !== id));
    removeQuestionInServer(id);
  };

  // Ask Peers section or Ask Mentor Section
  const [section, setSection] = useState<boolean>(true);
  const changeSection = (sectionVal:boolean): void => {
    setSection(sectionVal);
  };

  // if QuestionItems' count is > 15, we should make scroll Applicable
  const scrollClassName = questionsWithId.length > 5 ? 'questionItemsScroll' : '';
  const AskQuestionClassName = questionsWithId.length > 5 ? 'askQuestionsScroll' : '';

  return (
    <div className="inner">
      <div className="container">
        <div className="forum_header">
          <h1 className="forum_header-title">Discussion Forum</h1>
          <div className="forum_header-inner">
            <ul className="forum_header-menu">
              <li className={`forum_header-list forum_header-list${section ? '_active' : ''}`} onClick={() => { changeSection(true); }}>Ask Peers</li>
              <li className={`forum_header-list forum_header-list${!section ? '_active' : ''}`} onClick={() => { changeSection(false); }}>Ask Mentor</li>
            </ul>
            <button data-testid="toggle-btn" type="button" onClick={onOpen} className={`btn ${AskQuestionClassName}`}>Ask Question</button>
          </div>
        </div>
        <div className={scrollClassName}>
          {
            section ? (
              questionsWithId.length ? (
                questionsWithId.map((val) => (
                  <QuestionItem
                    removeQ={removeQuestion}
                    questionChanged={questionChanged}
                    text={val.text}
                    id={val.id}
                    createdDate={val.createdDate}
                    key={val.id}
                  />
                ))
              )
                : (
                  <div className="noQuestionsDiv">No questions. Be the First to Ask a question!</div>
                )
            )
              : (
                <div style={{
                  color: '#f03838', fontSize: '20px', marginLeft: '8px', marginTop: '10px',
                }}
                >
                  Ask Mentor section is not created yet!
                </div>
              )
          }
          {isLoading && <div style={{ marginTop: '40px' }}><Loader /></div>}
        </div>
      </div>
      {
        isOpen
        && (
        <AskQuestionPopup data-testid="askQuestionPopup" value={value} isOpen={isOpen} onClose={onClose} handleChange={handleChange} addQuestion={addQuestion} />
        )
      }
    </div>
  );
}

export default DiscussionForum;
