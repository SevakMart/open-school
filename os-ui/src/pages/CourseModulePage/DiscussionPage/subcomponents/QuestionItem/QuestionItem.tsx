/* eslint-disable react/prop-types */
import './questionItem.scss';
import { useState } from 'react';
import ArrowRightIcon from '../../../../../assets/svg/ArrowRight.svg';
import edit from '../../../../../assets/svg/edit.svg';
import save from '../../../../../assets/svg/save.svg';
import trash from '../../../../../assets/svg/trash.svg';

interface QuestionItemProps {
  removeQ(questionId: string): void;
  questionChanged(id: string, val: string): void;
  text: string;
  id: string;
}

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
                <textarea className="edit_question_textArea" value={editValue} onChange={editValueChange} id="fname" name="fname" />
              )
              : (
                <p className="Question_text">
                  <span className="Question__text_inner">{text}</span>
                </p>
              )
          }
        </div>
      </div>
    </div>
  );
};

export default QuestionItem;
