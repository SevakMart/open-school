import { useEffect, useState } from 'react';
import fetchData from '../../../services/fetchData';
import './discussionForm.scss';
import './discussionForm_media.scss';
import QuestionItem from './subcomponents/QuestionItem/QuestionItem';

function DiscussionForm(): JSX.Element {
  const questions = ['Title Of the question3', 'Title Of the question3', 'Title Of the question3'];
  const [buttonType, setButtonType] = useState<boolean>(false);
  const BtnName: string = buttonType ? 'Send' : 'Ask Question'; // "ASK Question" or Send
  const [value, setValue] = useState<string>('ask question');

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

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setValue(event.target.value);
  };

  const handleAskquestion = (): void => {
    setButtonType(() => !buttonType);
    if (BtnName === 'Ask Question') {
      setValue('');
    }
    if (BtnName === 'Send') {
      postQuestion();
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
            {
              buttonType === true
              && (
                <div>
                  <input className="question_input" type="text" id="fname" name="fname" value={value} onChange={handleChange} />
                </div>
              )
            }
            <button type="button" onClick={handleAskquestion} disabled={!value} className="btn">{BtnName}</button>
          </div>
        </div>
        {questions.map((val, i) => <QuestionItem text={val} num={i + 1} key={i} />)}
      </div>
    </div>
  );
}

export default DiscussionForm;
