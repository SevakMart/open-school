import React, { useState, useEffect, useRef } from 'react';
import './askQuestionPopup.scss';

interface PopupProps {
  value: string;
  isOpen: boolean;
  onClose: () => void;
  handleChange: (event: React.ChangeEvent<HTMLTextAreaElement>) => void;
  addQuestion: (val: string) => void;
}

const AskQuestionPopup: React.FC<PopupProps> = ({
  isOpen, onClose, value, handleChange, addQuestion,
}) => {
  const [isAnimating, setIsAnimating] = useState<boolean>(false);
  const textAreaRef = useRef<HTMLTextAreaElement>(null);

  useEffect(() => {
    if (isOpen && textAreaRef.current) {
      textAreaRef.current.focus();
    }
  }, [isOpen]);

  const animatedFunction = (any_function: (...args: string[]) => void, ...args: string[]): void => {
    setIsAnimating(true);
    setTimeout(() => {
      setIsAnimating(false);
      any_function(...args);
    }, 300);
  };

  return (
    <div className={`popup ${isOpen ? 'open' : ''} ${isAnimating ? 'animating' : ''}`}>
      <div className="popup-overlay" onClick={() => { animatedFunction(onClose); }} />
      <div className="popup-content">
        <div className="popup-title">Asq Question</div>
        <button type="button" className="close-button" onClick={() => { animatedFunction(onClose); }}>x</button>
        <div className="question_textArea-div">
          <textarea className="question_textArea" id="fname" name="fname" ref={textAreaRef} value={value} onChange={handleChange} placeholder="Ask your question here" />
          <div className="buttons">
            <button type="button" onClick={() => { animatedFunction(onClose); }} className="btn_cancel">Cancel</button>
            <button type="button" onClick={() => { animatedFunction(addQuestion, value); }} disabled={!value} className="btn_post">Post</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AskQuestionPopup;
