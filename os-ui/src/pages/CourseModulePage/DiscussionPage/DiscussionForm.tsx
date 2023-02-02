import { useState } from 'react';
import './discussionForm.scss';
import './discussionForm_media.scss';
import QuestionItem from './subcomponents/QuestionItem/QuestionItem';

function DiscussionForm():JSX.Element {
  const questions = ['Title Of the question3', 'Title Of the question3', 'Title Of the question3'];
  const [buttonType, setButtonType] = useState<boolean>(false);
  const BtnName:string = buttonType ? 'Send' : 'Ask Question'; // "ASK Question" or Send
  const [value, setValue] = useState<string>('ask question');

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setValue(event.target.value);
  };

  const handleAskquestion = (): void => {
    setButtonType(() => !buttonType);
    if (BtnName === 'Ask Question') {
      setValue('');
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