import React, { useState, useEffect, useRef } from 'react';
import { PopupProps } from '../../interfaces/interfaces';
import './askQuestionPopup.scss';

const AskQuestionPopup: React.FC<PopupProps> = ({
  isOpen, onClose, value, handleChange, addQuestion,
}) => {
  const textAreaRef = useRef<HTMLTextAreaElement>(null);

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

  return (
    <div className={`popup ${isOpen ? 'open' : ''} ${isAnimating ? 'animating' : ''}`}>
      <div className="popup-overlay" data-testid="close-btn" onClick={() => { animatedFunction(onClose); }} />
      <div className="popup-content">
        <div className="popup-title">Asq Question</div>
        <button type="button" className="close-button" data-testid="close-x-btn" onClick={() => { animatedFunction(onClose); }}>x</button>
        <div className="question_textArea-div">
          <textarea className="question_textArea" data-testid="question-textarea" id="fname" name="fname" ref={textAreaRef} value={value} onChange={handleChange} placeholder="Ask your question here" />
          <div className="buttons">
            <button type="button" onClick={() => { animatedFunction(onClose); }} className="btn_cancel" data-testid="close-cancel-btn">Cancel</button>
            <button type="button" onClick={() => { animatedFunction(addQuestion, value); }} disabled={!value} data-testid="post-btn" className="btn_post">Post</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AskQuestionPopup;
