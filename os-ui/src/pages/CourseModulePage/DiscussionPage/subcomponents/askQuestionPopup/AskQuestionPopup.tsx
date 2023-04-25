import React, { useState, useEffect, useRef } from 'react';
import { useDispatch } from 'react-redux';
import { addQuestion } from '../../../../../redux/Slices/UpdateQuestionSlice';
import { PopupProps } from '../../interfaces/interfaces';
import './askQuestionPopup.scss';

const AskQuestionPopup: React.FC<PopupProps> = ({
  isOpen, handleClose, value, handleChange, enrolledCourseId, token, cleanTextField, sectionName,
}) => {
  const textAreaRef = useRef<HTMLTextAreaElement>(null);

  const dispatch = useDispatch();
  const handleAddQuestion = () => {
    cleanTextField();
    dispatch(addQuestion({
      enrolledCourseId, text: value, token, sectionName,
    }));
  };

  useEffect(() => {
    if (isOpen && textAreaRef.current) {
      textAreaRef.current.focus();
    }
  }, [isOpen]);

  const [isAnimating, setIsAnimating] = useState<boolean>(false);
  const animatedFunction = (any_function: (...args: string[]) => void, ...args: string[]): void => {
    setIsAnimating(true);
    setTimeout(() => {
      setIsAnimating(false);
      any_function(...args);
    }, 300);
  };

  const handleKeyDown = (event: React.KeyboardEvent<HTMLTextAreaElement>) => {
    if (event.key === 'Enter') {
      event.preventDefault(); // prevent newline in textarea
      animatedFunction(handleAddQuestion);
    }

    if (event.key === 'Escape') {
      event.preventDefault(); // prevent newline in textarea
      animatedFunction(handleClose);
    }
  };

  return (
    <div className={`popup ${isOpen ? 'open' : ''} ${isAnimating ? 'animating' : ''}`}>
      <div className="popup-overlay" data-testid="close-btn" onClick={() => { animatedFunction(handleClose); }} />
      <div className="popup-content">
        <div className="popup-title">Ask Question</div>
        <button type="button" className="close-button" data-testid="close-x-btn" onClick={() => { animatedFunction(handleClose); }}>x</button>
        <div className="question_textArea-div">
          <textarea
            className="question_textArea"
            data-testid="question-textarea"
            id="fname"
            name="fname"
            ref={textAreaRef}
            value={value}
            onChange={handleChange}
            placeholder="Ask your question here"
            onKeyDown={handleKeyDown}
          />
          <div className="buttons">
            <button type="button" onClick={() => { animatedFunction(handleClose); }} className="btn_cancel" data-testid="close-cancel-btn">Cancel</button>
            <button type="button" onClick={() => { animatedFunction(handleAddQuestion); }} disabled={!value} data-testid="post-btn" className="btn_post">Post</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AskQuestionPopup;
