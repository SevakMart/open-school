/* eslint-disable react/prop-types */
import './questionItem.scss';
import { useState, useEffect, useRef } from 'react';
import ArrowRightIcon from '../../../../../assets/svg/ArrowRight.svg';
import edit from '../../../../../assets/svg/edit.svg';
import save from '../../../../../assets/svg/save.svg';
import trash from '../../../../../assets/svg/trash.svg';
import { QuestionItemProps } from '../../interfaces/interfaces';

const QuestionItem: React.FC<QuestionItemProps> = ({
  removeQ, questionChanged, text, id,
}) => {
  const [isEditPressed, SetEditPresses] = useState<boolean>(false);
  const btnType = isEditPressed ? `${save}` : `${edit}`;
  const [editValue, SetEditValue] = useState<string>(text);

  const editValueChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    SetEditValue(event.target.value);
  };

  const editQuestion = (): void => {
    SetEditPresses((prev) => !prev);
    if (isEditPressed) {
      questionChanged(id, editValue);
    }
  };

  // for focus editInput
  const textAreaRef = useRef<HTMLTextAreaElement>(null);

  useEffect(() => {
    if (isEditPressed && textAreaRef.current) {
      textAreaRef.current.focus();
      textAreaRef.current.setSelectionRange(editValue.length, editValue.length);
    }
  }, [isEditPressed]);

  return (
    <div className="Questions_page">
      <div className="Questions_inner">
        <div className="Question_item">
          <div className="icons">
            <div className="changeQuestion">
              <img className="edit" src={btnType} alt="Edt" onClick={editQuestion} />
              <img className="trash" src={trash} alt="X" onClick={() => removeQ(id)} />
            </div>
            <img className="arrowRightIcon" src={ArrowRightIcon} alt=">" />
          </div>
          {
            isEditPressed
              ? (
                <textarea
                  className="edit_question_textArea"
                  value={editValue}
                  ref={textAreaRef}
                  onChange={editValueChange}
                  id="fname"
                  name="fname"
                  style={{ wordWrap: 'break-word', overflow: 'auto' }}
                />
              )
              : (
                <div className="question_text_container">
                  <p className="Question_text">
                    <span className="Question__text_inner" style={{ wordWrap: 'break-word', overflow: 'auto' }}>{text}</span>
                  </p>
                </div>
              )
          }
        </div>
      </div>
    </div>
  );
};

export default QuestionItem;
