import React, { useState, useEffect, useRef } from 'react';
import { useDispatch } from 'react-redux';
import { useTranslation } from 'react-i18next';
import ArrowRightIcon from '../../../../../assets/svg/ArrowRight.svg';
import edit from '../../../../../assets/svg/edit.svg';
import answer from '../../../../../assets/svg/chat-left.svg';
import avatar from '../../../../../assets/svg/Avatar.png';
import threeVerticalDots from '../../../../../assets/svg/three-dots-vertical.svg';
import next from '../../../../../assets/svg/next.svg';
import { QuestionItemProps } from '../../interfaces/interfaces';
import QuestionItemPopup from './subcomponents/QuestionItemPopup/QuestionItemPopup';
import { removeQuestion, updateQuestion } from '../../../../../redux/Slices/QuestionActionsSlice';
import { addAnswer } from '../../../../../redux/Slices/AnswerActionsSlice';
import Answeritem from './subcomponents/AnswerItem/AnswerItem';
import { formatDate } from '../../helpers/formatDate';
import './questionItem.scss';

const QuestionItem: React.FC<QuestionItemProps> = ({
  text,
  id,
  createdDate,
  token,
  enrolledCourseId,
  sectionName,
  responsesMap,
  searchQuery,
}) => {
  const [isEditPressed, setEditPresses] = useState<boolean>(false);
  const [editValue, setEditValue] = useState<string>(text);
  const [answerValue, setAnswerValue] = useState<string>('');

  // is all answers to a question appers
  const [allAnswersState, setAllAnswersState] = useState(false);
  const handleSetAllAnswersState = () => {
    setAllAnswersState((prev) => !prev);
  };

  // popUp open and close state
  const [isOpen, setIsOpen] = useState<boolean>(false);

  // animation for popUps
  const [isAnimating, setIsAnimating] = useState<boolean>(false);
  const animatedFunction = (any_function: (...args: string[]) => void, ...args: string[]): void => {
    setIsAnimating(true);
    setTimeout(() => {
      setIsAnimating(false);
      any_function(...args);
    }, 300);
  };

  const dispatch = useDispatch();

  // answer section
  const [isAnswerSectionOpen, setIsAnswerSectionOpen] = useState<boolean>(false);
  const handleAnswerSectionOpen = () => {
    setIsAnswerSectionOpen((prev) => !prev);
  };

  const onAnswerTextareaClose = () => {
    setAnswerValue('');
    setIsAnswerSectionOpen(false);
  };

  const isAnswerValueEmpty = answerValue.trim() !== '';
  const handleAddAsnswer = (questionId: string) => {
    if (isAnswerValueEmpty && answerValue.length < 500) {
      if (!allAnswersState) handleSetAllAnswersState();
      dispatch(addAnswer({
        enrolledCourseId, answerText: answerValue, token, sectionName, questionId,
      }));
      onAnswerTextareaClose();
    }
    return null;
  };

  // disable save button when it is nothing typed or if we don't change the text
  const isDisable = isEditPressed ? editValue.trim() === '' || editValue === text : false;

  const handleUpdateQuestion = (questionId:string) => {
    dispatch(updateQuestion({
      enrolledCourseId, questionId, newText: editValue, token, sectionName,
    }));
  };

  // open and close a popup
  const changeIsOpen = () => {
    if (isOpen) return;
    setIsOpen((true));
    setIsAnswerSectionOpen(false);
  };

  const onClose = () => {
    setEditPresses(false);
    setEditValue(text);
    setIsOpen(false);
  };

  const handleRemoveQuestion = (questionId:string) => {
    animatedFunction(onClose);
    dispatch(removeQuestion({
      enrolledCourseId, questionId, token, sectionName,
    }));
  };

  // show more ...
  const [isExpanded, setIsExpanded] = useState<boolean>(false);
  const questionTextRef = useRef<HTMLDivElement>(null);
  const [isQuestonLong, setIsQuestonLong] = useState<boolean>(false);

  const toggleShowMore = () => {
    setIsExpanded(!isExpanded);
  };

  const calculateLineCount = () => {
    const questionTextElement = questionTextRef.current;
    if (questionTextElement) {
      const lineHeight = parseFloat(getComputedStyle(questionTextElement).lineHeight);
      const height = questionTextElement.clientHeight;
      const lineCount = Math.round(height / lineHeight);
      return lineCount;
    }
    return 0;
  };

  useEffect(() => {
    const lineCount = calculateLineCount();
    setIsQuestonLong(lineCount >= 3);
  }, [text]);

  // edit
  const editValueChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    if (event.target.value.length > 500) event.preventDefault();
    else setEditValue(event.target.value);
  };

  const editQuestion = (): void => {
    setEditPresses((prev) => !prev);
    if (!editValue) {
      setEditValue(text);
    }

    if (isEditPressed) {
      handleUpdateQuestion(id);
      setIsOpen(false);
      setIsExpanded(false);
    } else {
      setIsOpen(false);
    }
  };

  // for focus edit and answer textarea
  const editTextAreaRef = useRef<HTMLTextAreaElement>(null);
  const answerTextAreaRef = useRef<HTMLTextAreaElement>(null);

  useEffect(() => {
    if (isEditPressed && editTextAreaRef.current) {
      editTextAreaRef.current.focus();
      editTextAreaRef.current.setSelectionRange(editValue.length, editValue.length);
    }
  }, [isEditPressed]);

  useEffect(() => {
    if (isAnswerSectionOpen && answerTextAreaRef.current) {
      answerTextAreaRef.current.focus();
      answerTextAreaRef.current.setSelectionRange(answerValue.length, answerValue.length);
    }
  }, [isAnswerSectionOpen]);

  // changeHandler for answers
  const changeHandlerAnswer = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    if (event.target.value.length > 500) event.preventDefault();
    else setAnswerValue(event.target.value);
  };

  // formatting data
  const formattedDate = formatDate(createdDate);

  const { t } = useTranslation();

  const highlightMatches = (text: string, searchQuery: string) => {
    if (!searchQuery || typeof searchQuery !== 'string') return text;

    const lowerCaseSearchQuery = searchQuery.toLowerCase();
    const lowerCaseText = text.toLowerCase();

    let startIndex = 0;
    const highlightedText: JSX.Element[] = [];

    while (startIndex < lowerCaseText.length) {
	  const index = lowerCaseText.indexOf(lowerCaseSearchQuery, startIndex);
	  if (index !== -1) {
        const prefix = text.substring(startIndex, index);
        const match = text.substring(index, index + searchQuery.length);
        highlightedText.push(<span key={startIndex}>{prefix}</span>);
        highlightedText.push(<span key={startIndex + 1} className="highlighted">{match}</span>);
        startIndex = index + searchQuery.length;
	  } else {
        highlightedText.push(<span key={startIndex}>{text.substring(startIndex)}</span>);
        break;
	  }
    }

    return <>{highlightedText}</>;
  };

  return (
    <div className="Questions_page">
      {
        isOpen && (
          <QuestionItemPopup
            isOpen={isOpen}
            isAnimating={isAnimating}
            animatedFunction={animatedFunction}
            onClose={onClose}
            isDisable={isDisable}
            editQuestion={editQuestion}
            btnType={edit}
            removeQ={handleRemoveQuestion}
            id={id}
            textAreaRef={editTextAreaRef}
          />
        )
      }
      <div className="Questions_inner">
        <div className="Question_item">
          {isEditPressed ? (
            <div className="editField">
              <textarea
                className="edit_question_textArea"
                value={editValue}
                ref={editTextAreaRef}
                onChange={editValueChange}
                id="fname"
                name="fname"
              />
              <div className="editField_count">
                {editValue.length}
                /500
              </div>
              <div className="editField_btns">
                <button type="button" className="btn_cancel" onClick={() => animatedFunction(onClose)}>{t('Cancel')}</button>
                <button type="button" className="btn_post" disabled={isDisable} onClick={() => animatedFunction(editQuestion)}>{t('Save')}</button>
              </div>
            </div>
          ) : (
            <div className="question_item">
              <div className="question__text_box">
                <div className="user_">{t('Me')}</div>
                <div className="question__text_inner-date" data-testid="questionItem-date">
                  {formattedDate}
                </div>
              </div>
              <div className="question-item_body">
                <div
                  className={`question-item_text ${!isExpanded && 'question-item_text_expanded'}`}
                  data-testid="questionItem-text"
                  style={{ wordWrap: 'break-word' }}
                  ref={questionTextRef}
                >
                  {highlightMatches(text, searchQuery)}
                </div>
                {isQuestonLong && (
                  <div className="question-item_showMore" onClick={toggleShowMore}>
                    {isExpanded ? 'Show less' : 'Show more'}
                    <img
                      className={`question-item_showMore_img ${isExpanded ? 'question-item_showMore_img_rotateIcon' : ''}`}
                      src={next}
                      alt=">"
                    />
                  </div>
                )}
              </div>
              <div className={`icons ${!isQuestonLong ? 'iconsToTop' : ''}`}>
                <img className="answer_icon" onClick={() => animatedFunction(handleAnswerSectionOpen)} src={answer} alt="->" />
                <div className="messageCount">5</div>
                <img className="icon_menu" src={threeVerticalDots} onClick={() => animatedFunction(changeIsOpen)} alt="menu" />
                <img className={`arrowRightIcon ${allAnswersState ? 'arrowRightIcon_rotate' : ''}`} onClick={() => animatedFunction(handleSetAllAnswersState)} src={ArrowRightIcon} alt=">" />
              </div>
            </div>
          )}
        </div>
      </div>
      {isAnswerSectionOpen && (
        <div className="answer_section">
          <div className="answer_section_body">
            <img src={avatar} alt="I" className="answer_section_avatarka" />
            <textarea ref={answerTextAreaRef} value={answerValue} onChange={changeHandlerAnswer} className="answer_section_textarea" placeholder="Add your answer here" />
          </div>
          <div className="answer_section_count">
            {answerValue.length}
            /500
          </div>
          <div className="answer_section_actions">
            <button type="button" onClick={() => animatedFunction(onAnswerTextareaClose)} className="btn_cancel" disabled={isAnimating}>{t('Cancel')}</button>
            <button type="button" onClick={() => animatedFunction(handleAddAsnswer, id)} className="btn_post" disabled={isAnimating}>{t('Post')}</button>
          </div>
        </div>
      )}
      {allAnswersState && (
        <div className={`allAnswers ${allAnswersState ? 'allAnswersOpen' : ''}`}>
          {responsesMap.map((item) => {
            if (item.questionId.questionId === id) {
              return (
                <Answeritem
                  key={item.questionId.idAnswer}
                  name={item.questionId.name}
                  surname={item.questionId.surname}
                  answerText={item.questionId.answerText}
                  answerCreatedDate={item.questionId.answerCreatedDate}
                  avatar={avatar}
                />
              );
            }
            return null;
          })}
        </div>
      )}
    </div>
  );
};

export default QuestionItem;
