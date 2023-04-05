import { useState, useMemo } from 'react';
import { useSelector } from 'react-redux';
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
    setValue(event.target.value);
  };

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

  // function for forming an object with typed question and generated id for it
  const [questionsWithId, setQuestionsWithId] = useState<Question[]>([]);

  const addToQuestionObjArr = (val: string): void => {
    const newQuestion: Question = {
      id: Date.now().toString(),
      text: val,
    };
    setQuestionsWithId((prevQuestion) => [newQuestion, ...prevQuestion]);
  };

  // edit text
  const questionChanged = (id: string, newText: string): void => {
    setQuestionsWithId((prevQuestions) => prevQuestions
      .map((q) => (q.id === id ? { ...q, text: newText } : q)));
  };

  // get course informatiom
  const courseDescriptionState = useSelector<RootState>((state) => state.courseDescriptionRequest) as { entity: CourseDescriptionType, isLoading: boolean, errorMessage: string };
  const { entity } = courseDescriptionState;

  // Post question
  const postQuestion = async () => {
    const body = {
      text: value,
    };

    const response = await (await fetchData.post(`courses/enrolled/${entity.enrolledCourseId}/peers-questions`, body, {}, idAndToken.token)).json();
    return response;
  };

  // delete question from server
  // const deleteQuestionFromServer = async () => {
  //   const response = await (await fetchData.delete(`courses/enrolled/${entity.enrolledCourseId}/peers-questions${courceInfo.id}`, {}, idAndToken.token)).json();
  //   return response;
  // };

  // add question to the addToQuestionObjArr and post it
  const addQuestion = (val: string): void => {
    val && addToQuestionObjArr(val);
    postQuestion();
    onClose();
  };
  // removes question
  const removeQusetion = (questionId: string): void => {
    setQuestionsWithId((prevQuestions) => prevQuestions.filter((q) => q.id !== questionId));
  };

  return (
    <div className="inner">
      <div className="container">
        <div className="forum_header">
          <h1 className="forum_header-title">Discussion Forum</h1>
          <div className="forum_header-inner">
            <ul className="forum_header-menu">
              <li className="forum_header-list">Ask Peeps</li>
              <li className="forum_header-list">Ask Mentor</li>
            </ul>
            <button data-testid="toggle_btn" type="button" onClick={onOpen} className="btn">Ask Question</button>
          </div>
        </div>
        {questionsWithId.map((val) => (
          <QuestionItem
            removeQ={removeQusetion}
            questionChanged={questionChanged}
            text={val.text}
            id={val.id}
            key={val.id}
          />
        ))}
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
