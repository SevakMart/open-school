import React, { useState, useEffect, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import { addQuestion } from '../../../../../redux/Slices/QuestionActionsSlice';
import { PopupProps } from '../../interfaces/interfaces';
import avatar from '../../../../../assets/svg/Avatar.png';
import './askQuestionPopup.scss';

const AskQuestionPopup: React.FC<PopupProps> = ({
  isOpen, handleClose, value, handleChange, enrolledCourseId, token, cleanTextField, sectionName,
}) => {
  const textAreaRef = useRef<HTMLTextAreaElement>(null);

  // animating function
  const [isAnimating, setIsAnimating] = useState<boolean>(false);
  const animatedFunction = (any_function: (...args: string[]) => void, ...args: string[]): void => {
    setIsAnimating(true);
    setTimeout(() => {
      setIsAnimating(false);
      any_function(...args);
    }, 300);
  };

  const isValueEmpty = value.trim() === '';
  const isValueLong = value.length > 500;
  const isDisabled = isValueEmpty || isValueLong || isAnimating;

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

  // close the popUp when escape pressed
  const handleKeyDown = (event: React.KeyboardEvent<HTMLTextAreaElement>) => {
    if (event.key === 'Escape') {
      event.preventDefault(); // prevent newline in textarea
      animatedFunction(handleClose);
    }
  };

  const { t } = useTranslation();

  return (
    <div className={`popup ${isOpen ? 'open' : ''} ${isAnimating ? 'animating' : ''}`}>
      <div className="popup-overlay" data-testid="close-btn" onClick={() => { animatedFunction(handleClose); }} />
      <div className="popup-content">
        <div className="popup-title">{t('Ask Question')}</div>
        <button type="button" className="close-button" data-testid="close-x-btn" onClick={() => { animatedFunction(handleClose); }}>{t('×')}</button>
        <div className="question_textArea_item">
          <div className="question_textArea_box">
            <img src={avatar} alt="I" className="question_textArea_box_avatarka" />
            <div className={`question_textArea_inner ${isValueLong ? 'question_textArea_inner_disabled' : ''}`}>
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
            </div>
          </div>
          <div className="question_textArea_count">
            {value.length}
            /500
          </div>
          <div className="buttons">
            <button type="button" onClick={() => animatedFunction(handleClose)} disabled={isDisabled} className="btn_cancel" data-testid="close-cancel-btn">{t('Cancel')}</button>
            <button type="button" onClick={() => animatedFunction(handleAddQuestion)} disabled={isDisabled} data-testid="post-btn" className="btn_post">{t('Post')}</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AskQuestionPopup;
