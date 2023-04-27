import { useState, useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useLocation, useParams } from 'react-router-dom';
import Loader from '../../../component/Loader/Loader';
import {
  changeSection, onClose, onOpen,
} from '../../../redux/Slices/QuestionActionsSlice';
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
  const AllQuestionsState = useSelector<RootState>((state) => state.QuestionActions) as {
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
  }), [userInfo]);

  // if QuestionItems' count is > 15, we should make scroll Applicable
  const scrollClassName = questionsWithId.length > 15 ? 'questionItemsScroll' : '';
  const AskQuestionClassName = questionsWithId.length > 15 ? 'askQuestionsScroll' : '';

  // Link to the askPeers or askMentor pages
  const sectionName = section ? 'peers' : 'mentor';
  const { courseId } = useParams();
  const location = useLocation();
  const currentPath = location.pathname;
  let isBtnClicked = true;
  if (currentPath === `/userCourse/modulOverview/${courseId}/discussionForum/AskPeers`) isBtnClicked = true;
  if (currentPath === `/userCourse/modulOverview/${courseId}/discussionForum/AskMentor`) isBtnClicked = false;

  // set the value of section to true initially when DiscussionForum opens
  dispatch(changeSection(true));

  const { t } = useTranslation();

  return (
    <div className="inner">
      <div className="container">
        <div className="forum_header">
          <h1 className="forum_header-title">{t('Discussion Forum')}</h1>
          <div className="forum_header-inner">
            <ul className="forum_header-menu">
              <button disabled={isLoading} type="button" className={`forum_header-list forum_header-list${isBtnClicked ? '_active' : ''}`}>
                <Link to={`/userCourse/modulOverview/${courseId}/discussionForum/AskPeers`} className="forum_header-list_link" onClick={() => dispatch(changeSection(true))}>
                  {t('Ask Peers')}
                </Link>
              </button>
              <button disabled={isLoading} type="button" className={`forum_header-list forum_header-list${!isBtnClicked ? '_active' : ''}`} onClick={() => dispatch(changeSection(false))}>
                <Link to={`/userCourse/modulOverview/${courseId}/discussionForum/AskMentor`} className="forum_header-list_link">
                  {t('Ask Mentor')}
                </Link>
              </button>

            </ul>
            <button data-testid="toggle-btn" type="button" onClick={() => dispatch(onOpen())} className={`btn ${AskQuestionClassName}`}>{t('Ask Question')}</button>
          </div>
        </div>
        <div className={scrollClassName}>
          {
            isBtnClicked ? (
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
                  <div className="noQuestionsDiv">{t('No questions. Be the First to Ask a question!')}</div>
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
                    <div className="noQuestionsDiv">{t('No questions to mentors. Be the First to Ask a question!')}</div>
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
