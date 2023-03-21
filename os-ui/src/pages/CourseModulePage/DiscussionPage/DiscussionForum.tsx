import {
  useEffect, useState, useRef, useMemo,
} from 'react';
import { useParams } from 'react-router-dom';
import fetchData from '../../../services/fetchData';
import './DiscussionForum.scss';
import { Question } from './interfaces/interfaces';
import QuestionItem from './subcomponents/QuestionItem/QuestionItem';

function DiscussionForum({ userInfo }:{userInfo:object}): JSX.Element {
  const { courseId } = useParams();
  const [buttonType, setButtonType] = useState<boolean>(false);
  const BtnName: string = buttonType ? 'Send' : 'Ask Question'; // "ASK Question" or Send
  const [value, setValue] = useState<string>('ask question');
  const [questionsWithId, setQuestionsWithId] = useState<Question[]>([]);

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

  const addQuestion = (val: string): void => {
    addToQuestionObjArr(val);
  };

  const removeQusetion = (questionId: string): void => {
    setQuestionsWithId((prevQuestions) => prevQuestions.filter((q) => q.id !== questionId));
  };

  const textAreaRef = useRef<HTMLTextAreaElement>(null);

  const findEnrId = (arr: any) => {
    let courseEnrolledId = 0;
    for (let i = 0; i < arr.length; i += 1) {
      const current = Number(arr[i].courseId);
      console.log(arr[i].courseId);
      console.log(courseId, 'courseId');
      if (current && courseId === current) {
        courseEnrolledId = arr[i].id;
        console.log('courceEnrolledId', courseEnrolledId);
        return courseEnrolledId;
      }
    }
    return "didn't work correctly";
  };

  const getAllEnrolledCources = async () => {
    const response = await (await fetchData.get(`users/${idAndToken.id}/courses/enrolled?courseStatusId=1`, {}, idAndToken.token)).json();
    console.log('response', response);
    return findEnrId(response); // Return the enrolled course ID from the function
  };

  const postQuestion = async () => {
    const body = {
      text: value,
    };
    const eId = await getAllEnrolledCources();
    const response = await (await fetchData.post(`courses/enrolled/${eId}/peers-question`, body, {}, idAndToken.token)).json();
    console.log(response);
    console.log('id', idAndToken.id || null, 'token', idAndToken.token);
    return response;
  };

  const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setValue(event.target.value);
  };

  useEffect(() => {
    if (buttonType && textAreaRef.current) {
      textAreaRef.current.focus();
    }
  }, [buttonType]);

  const handleAskquestion = (): void => {
    setButtonType(() => !buttonType);
    if (BtnName === 'Ask Question') {
      setValue('');
    }
    if (BtnName === 'Send') {
      postQuestion();
      addQuestion(value);
    }
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
            <button type="button" onClick={handleAskquestion} disabled={!value} className="btn">{BtnName}</button>
          </div>
        </div>
        {
          buttonType === true
          && (
            <div className="question_textArea-div">
              <textarea ref={textAreaRef} className="question_textArea" id="fname" name="fname" value={value} onChange={handleChange} />
            </div>
          )
        }
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
    </div>
  );
}

export default DiscussionForum;
