import { useEffect, useState, useRef } from 'react';
import fetchData from '../../../services/fetchData';
import './discussionForm.scss';
import './discussionForm_media.scss';
import QuestionItem from './subcomponents/QuestionItem/QuestionItem';

function DiscussionForm(): JSX.Element {
  const [buttonType, setButtonType] = useState<boolean>(false);
  const BtnName: string = buttonType ? 'Send' : 'Ask Question'; // "ASK Question" or Send
  const [value, setValue] = useState<string>('ask question');
  const [questions, setAddStrings] = useState<string[]>([]);

  const addQuestion = (val: string): void => {
    setAddStrings([val, ...questions]); // add a new string to the beginning of the array
  };

  const textAreaRef = useRef<HTMLTextAreaElement>(null);

  const postQuestion = async () => {
    const body = {
      text: value,
      courceId: 0,
    };
    const response = await fetchData.post('discussionQuestions', body, {}, 'e65abc20-e264-4a1c-ae1c-28c010533c9f6');
    console.log(response);
    console.log(body);
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
          <h1 className="forum_header-title">Discussion Form</h1>
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
        {questions.map((val, i) => <QuestionItem text={val} key={i} />)}
      </div>
    </div>
  );
}

export default DiscussionForm;
