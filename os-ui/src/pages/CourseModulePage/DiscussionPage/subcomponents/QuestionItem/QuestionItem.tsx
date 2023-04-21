import './questionItem.scss';
import { useState, useEffect, useRef } from 'react';
import { useDispatch } from 'react-redux';
import ArrowRightIcon from '../../../../../assets/svg/ArrowRight.svg';
import edit from '../../../../../assets/svg/edit.svg';
import save from '../../../../../assets/svg/save.svg';
import threeVerticalDots from '../../../../../assets/svg/three-dots-vertical.svg';
import { QuestionItemProps } from '../../interfaces/interfaces';
import QuestionItemPopup from './QuestionItemPopup/QuestionItemPopup';
import { removeQuestion, updateQuestion } from '../../../../../redux/Slices/AskQuestionSlice';

/* eslint-disable react/prop-types */
const QuestionItem: React.FC<QuestionItemProps> = ({
  text, id, createdDate, token, enrolledCourseId, sectionName,
}) => {
  const [isEditPressed, SetEditPresses] = useState<boolean>(false);
  const btnType = isEditPressed ? `${save}` : `${edit}`;
  const btnTextType = isEditPressed ? 'Save' : 'Edit';
  const [editValue, SetEditValue] = useState<string>(text);

  const dispatch = useDispatch();
  const handleUpdateQuestion = (questionId:string) => {
    dispatch(updateQuestion({
      enrolledCourseId, questionId, newText: editValue, token, sectionName,
    }));
  };

  const handleRemoveQuestion = (questionId:string) => {
    dispatch(removeQuestion({
      enrolledCourseId, questionId, token, sectionName,
    }));
  };

  // popUp open and close state
  const [isOpen, setIsOpen] = useState<boolean>(false);

  // edit
  const editValueChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    if (event.target.value.length > 500) event.preventDefault();
    else SetEditValue(event.target.value);
  };

  const editQuestion = (): void => {
    SetEditPresses((prev) => !prev);
    if (isEditPressed) {
      handleUpdateQuestion(id);
      setIsOpen(false);
    }
    if (!editValue) {
      handleUpdateQuestion(id);
      SetEditValue(text);
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

  // popup for edit and remove btns
  const [isAnimating, setIsAnimating] = useState<boolean>(false);
  const animatedFunction = (any_function: (...args: string[]) => void, ...args: string[]): void => {
    setIsAnimating(true);
    setTimeout(() => {
      setIsAnimating(false);
      any_function(...args);
    }, 300);
  };

  // open and close a popup
  const onOpen = () => {
    setIsOpen(true);
  };

  const onClose = () => {
    SetEditPresses(false);
    if (!editValue) {
      SetEditValue(text);
    }
    // questionChanged(id, editValue);
    setIsOpen(false);
  };

  // formatting data
  const date = new Date(createdDate);
  const formattedDate = date.toLocaleString('en-US', {
    month: 'long',
    day: 'numeric',
    year: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
    second: 'numeric',
    // timeZoneName: 'short',
  });

  return (
    <div className="Questions_page">
      {
        isOpen && (
          <QuestionItemPopup
            isOpen={isOpen}
            isAnimating={isAnimating}
            animatedFunction={animatedFunction}
            onClose={onClose}
            editQuestion={editQuestion}
            btnType={btnType}
            btnTextType={btnTextType}
            removeQ={handleRemoveQuestion}
            id={id}
            textAreaRef={textAreaRef}
          />
        )
      }
      <div className="Questions_inner">
        <div className="Question_item">
          <div className="icons">
            <img className="icon_menu" src={threeVerticalDots} onClick={() => { animatedFunction(onOpen); }} alt="menu" />
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
                <div className="question_item">
                  <div className="Question_text_" data-testid="questionItem-text" style={{ wordWrap: 'break-word' }}>{text}</div>
                  <div className="question__text_box">
                    <div className="user_">Me</div>
                    <div className="Question__text_inner-date" data-testid="questionItem-date">{formattedDate}</div>
                  </div>
                </div>
              )
          }
        </div>
      </div>
    </div>
  );
};

export default QuestionItem;
