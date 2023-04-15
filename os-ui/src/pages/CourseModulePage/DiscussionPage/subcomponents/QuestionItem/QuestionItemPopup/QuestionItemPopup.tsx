import React, { useEffect, useRef } from 'react';
import { QuestionItemPopupProps } from '../../../interfaces/interfaces';
import trash from '../../../../../../assets/svg/trash.svg';
import './questionItemPopup.scss';

const QuestionItemPopup:React.FC<QuestionItemPopupProps> = ({
  isOpen, isAnimating, animatedFunction, onClose, editQuestion, btnType, btnTextType, removeQ, id, textAreaRef,
}) => {
  const popupRef = useRef<HTMLDivElement | null>(null);
  const bodyRef = useRef(document.body);

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
  }, []);

  return (
    <div ref={popupRef} className={`btnsPopup ${isOpen ? 'open' : ''} ${isAnimating ? 'animating' : ''}`}>
      <div className="btnsPopup-content">
        <div className="btnsPopup-content_item" onClick={editQuestion}>
          <img className="edit" src={btnType} alt="Edt" />
          <div className="editBtn-title">{btnTextType}</div>
        </div>
        <div className="btnsPopup-content_item btnsPopup-content_item_margin" data-testid="remove-btn" onClick={() => removeQ(id)}>
          <img className="trash" src={trash} alt="X" />
          <div className="removeBtn-title">Remove</div>
        </div>
      </div>
    </div>
  );
};

export default QuestionItemPopup;
