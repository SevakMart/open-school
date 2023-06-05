import React, { useEffect, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { QuestionItemPopupProps } from '../../../../interfaces/interfaces';
import trash from '../../../../../../../assets/svg/trash.svg';
import './questionItemPopup.scss';

const QuestionItemPopup:React.FC<QuestionItemPopupProps> = ({
  isOpen, isAnimating, animatedFunction, onClose, isDisable, editQuestion, btnType, removeQ, id, textAreaRef,
}) => {
  const popupRef = useRef<HTMLDivElement | null>(null);
  const bodyRef = useRef(document.body);

  // when click outside the popup, close it
  const handleClickOutside = (event: MouseEvent) => {
    const target = event.target as Node;
    if (popupRef.current && !popupRef.current.contains(target) && target !== textAreaRef.current) {
      animatedFunction(onClose);
    }
  };

  useEffect(() => {
    const listener = (event: MouseEvent) => handleClickOutside(event);
    bodyRef.current.addEventListener('mousedown', listener);
    return () => {
      bodyRef.current.removeEventListener('mousedown', listener);
    };
  }, [popupRef, textAreaRef]);

  const { t } = useTranslation();

  return (
    <div ref={popupRef} className={`btnsPopup ${isOpen ? 'open' : ''} ${isAnimating ? 'animating' : ''}`}>
      <div className="btnsPopup-content">
        <button disabled={isDisable} type="button" className="btnsPopup-content_item" onClick={editQuestion}>
          <img className="edit" src={btnType} alt="edit" />
          <div className="editBtn-title">{t('Edit')}</div>
        </button>
        <button type="button" className="btnsPopup-content_item btnsPopup-content_item_margin" data-testid="remove-btn" onClick={() => removeQ(id)}>
          <img className="trash" src={trash} alt="X" />
          <div className="removeBtn-title">{t('Remove')}</div>
        </button>
      </div>
    </div>
  );
};

export default QuestionItemPopup;
