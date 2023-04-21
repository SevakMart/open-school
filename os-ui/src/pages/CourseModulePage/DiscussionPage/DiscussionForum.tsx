import { useState, useMemo } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Loader from '../../../component/Loader/Loader';
import {
  changeSection, onClose, onOpen,
} from '../../../redux/Slices/AskQuestionSlice';
import { RootState } from '../../../redux/Store';
import { CourseDescriptionType } from '../../../types/CourseTypes';
import './DiscussionForum.scss';
import { Question } from './interfaces/interfaces';
import AskQuestionPopup from './subcomponents/askQuestionPopup/AskQuestionPopup';
import QuestionItem from './subcomponents/QuestionItem/QuestionItem';

const DiscussionForum = ({ userInfo }:{userInfo:object}): JSX.Element => {
  // save typed question
  const [value, setValue] = useState<string>('');
  const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    if (event.target.value.length > 500) event.preventDefault();
    else setValue(event.target.value);
  };

  // get course informatiom
  const courseDescriptionState = useSelector<RootState>((state) => state.courseDescriptionRequest) as { entity: CourseDescriptionType, isLoading: boolean, errorMessage: string };
  const { entity } = courseDescriptionState;

  const dispatch = useDispatch();
  const AllQuestionsState = useSelector<RootState>((state) => state.AskQuestion) as {
     questionsWithId: Question[],
     questionsWithIdToMentor: Question[],
     isLoading: boolean,
     isOpen: boolean,
     section: boolean,
    };

  const {
    questionsWithId, questionsWithIdToMentor, isLoading, isOpen, section,
  } = AllQuestionsState;

  const handleClose = () => {
    setValue('');
    dispatch(onClose());
  };

  const cleanTextField = () => {
    setValue('');
  };

  // get id and token of user
  const idAndToken = useMemo(() => ({
    token: (userInfo as any).token,
    id: (userInfo as any).id,
  }), []);

  // if QuestionItems' count is > 15, we should make scroll Applicable
  const scrollClassName = questionsWithId.length > 5 ? 'questionItemsScroll' : '';
  const AskQuestionClassName = questionsWithId.length > 5 ? 'askQuestionsScroll' : '';

  const sectionName = section ? 'peers' : 'mentor';

  return (
    <div className="inner">
      <div className="container">
        <div className="forum_header">
          <h1 className="forum_header-title">Discussion Forum</h1>
          <div className="forum_header-inner">
            <ul className="forum_header-menu">
              <li className={`forum_header-list forum_header-list${section ? '_active' : ''}`} onClick={() => dispatch(changeSection(true))}>Ask Peers</li>
              <li className={`forum_header-list forum_header-list${!section ? '_active' : ''}`} onClick={() => dispatch(changeSection(false))}>Ask Mentor</li>
            </ul>
            <button data-testid="toggle-btn" type="button" onClick={() => dispatch(onOpen())} className={`btn ${AskQuestionClassName}`}>Ask Question</button>
          </div>
        </div>
        <div className={scrollClassName}>
          {
            section ? (
              questionsWithId.length ? (
                questionsWithId.map((val) => (
                  <QuestionItem
                    text={val.text}
                    id={val.id}
                    createdDate={val.createdDate}
                    key={val.id}
                    token={idAndToken.token}
                    enrolledCourseId={entity.enrolledCourseId}
                    sectionName={sectionName}
                  />
                ))
              )
                : (
                  <div className="noQuestionsDiv">No questions. Be the First to Ask a question!</div>
                )
            )
              : (
                questionsWithIdToMentor.length ? (
                  questionsWithIdToMentor.map((val) => (
                    <QuestionItem
                      text={val.text}
                      id={val.id}
                      createdDate={val.createdDate}
                      key={val.id}
                      token={idAndToken.token}
                      enrolledCourseId={entity.enrolledCourseId}
                      sectionName={sectionName}
                    />
                  ))
                )
                  : (
                    <div className="noQuestionsDiv">No questions to mentors. Be the First to Ask a question!</div>
                  )
              )
          }
          {isLoading && <div style={{ marginTop: '40px' }}><Loader /></div>}
        </div>
      </div>
      {
        isOpen
        && (
        <AskQuestionPopup
          data-testid="askQuestionPopup"
          value={value}
          isOpen={isOpen}
          handleClose={handleClose}
          handleChange={handleChange}
          enrolledCourseId={entity.enrolledCourseId}
          token={idAndToken.token}
          cleanTextField={cleanTextField}
          sectionName={sectionName}
        />
        )
      }
    </div>
  );
};

export default DiscussionForum;
