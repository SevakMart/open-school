import { useState, useMemo } from 'react';
// import { useParams } from 'react-router-dom';
import fetchData from '../../../services/fetchData';
import './DiscussionForum.scss';
import { Question } from './interfaces/interfaces';
import AskQuestionPopup from './subcomponents/askQuestionPopup/AskQuestionPopup';
import QuestionItem from './subcomponents/QuestionItem/QuestionItem';

function DiscussionForum({ userInfo }:{userInfo:object}): JSX.Element {
  // const { courseId } = useParams();
  const [value, setValue] = useState<string>('');
  const [questionsWithId, setQuestionsWithId] = useState<Question[]>([]);

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

  const idAndToken = useMemo(() => ({
    token: (userInfo as any).token,
    id: (userInfo as any).id,
  }), []);

  const addToQuestionObjArr = (val: string): void => {
    const newQuestion: Question = {
      id: Date.now().toString(),
      text: val,
    };
    setQuestionsWithId((prevQuestion) => [newQuestion, ...prevQuestion]);
  };

  const questionChanged = (id: string, newText: string): void => {
    setQuestionsWithId((prevQuestions) => prevQuestions
      .map((q) => (q.id === id ? { ...q, text: newText } : q)));
  };

  const postQuestion = async () => {
    const body = {
      text: value,
    };

    const response = await (await fetchData.post('courses/enrolled/42/peers-question', body, {}, idAndToken.token)).json();
    console.log(response);
    console.log('id', idAndToken.id || null, 'token', idAndToken.token);
    return response;
  };

  const addQuestion = (val: string): void => {
    val && addToQuestionObjArr(val);
    postQuestion();
    onClose();
  };

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
            <button type="button" onClick={onOpen} className="btn">Ask Question</button>
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
            <AskQuestionPopup value={value} isOpen={isOpen} onClose={onClose} handleChange={handleChange} addQuestion={addQuestion} />
          )
        }
    </div>
  );
}

export default DiscussionForum;
