import { useState, useMemo, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useLocation, useParams } from 'react-router-dom';
import Loader from '../../../component/Loader/Loader';
import { changeSection, onClose, onOpen } from '../../../redux/Slices/QuestionActionsSlice';
import { RootState } from '../../../redux/Store';
import { CourseDescriptionType } from '../../../types/CourseTypes';
import './DiscussionForum.scss';
import { Question, ResponsesMap } from './interfaces/interfaces';
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

  // Link to the askPeers or askMentor pages
  const sectionName = section ? 'peers' : 'mentor';
  const { courseId } = useParams();
  const location = useLocation();
  const currentPath = location.pathname;
  let isBtnClicked = true;
  if (currentPath === `/userCourse/modulOverview/${courseId}/discussionForum/AskPeers`) isBtnClicked = true;
  if (currentPath === `/userCourse/modulOverview/${courseId}/discussionForum/AskMentor`) isBtnClicked = false;

  // This will call the changeSection action with the true value only once when the component mounts.
  useEffect(() => {
    dispatch(changeSection(true));
  }, []);

  // mentor or peersF
  const questionsMap:Question[] = isBtnClicked ? questionsWithId : questionsWithIdToMentor;

  const { t } = useTranslation();

  // get all AnswerStates
  const AllAnswerState = useSelector<RootState>((state) => state.AnswerActions) as {
    PeersResponses: ResponsesMap[],
    MentorResponses: ResponsesMap[],
    isLoading: boolean,
  };

  const {
    PeersResponses, MentorResponses, isLoading: isLoadingAnswers,
  } = AllAnswerState;

  const responsesMap: ResponsesMap[] = isBtnClicked ? PeersResponses : MentorResponses;

  // if Items' count is > ~15, we should make scroll Applicable
  const scrollClassName = questionsWithId.length + responsesMap.length > 6 ? 'questionItemsScroll' : '';
  const AskQuestionClassName = questionsWithId.length + responsesMap.length > 6 ? 'askQuestionsScroll' : '';

  return (
    <div className="inner">
      <div className="container">
        <div className="forum_header">
          <h1 className="forum_header-title">{t('Discussion Forum')}</h1>
          <div className="forum_header-inner">
            <ul className="forum_header-menu">
              <button disabled={isLoading} type="button" className="forum_header-list forum_header-list" onClick={() => dispatch(changeSection(true))}>
                <Link to={`/userCourse/modulOverview/${courseId}/discussionForum/AskPeers`} className={`forum_header-list_link forum_header-list_link${isBtnClicked ? '_active' : ''}`}>
                  {t('Ask Peers')}
                </Link>
                <div className={`forum_header-list_underLine forum_header-list_underLine${isBtnClicked ? '_active' : ''}`} />
              </button>
              <button disabled={isLoading} type="button" className="forum_header-list forum_header-list" onClick={() => dispatch(changeSection(false))}>
                <Link to={`/userCourse/modulOverview/${courseId}/discussionForum/AskMentor`} className={`forum_header-list_link forum_header-list_link${isBtnClicked ? '' : '_active'}`}>
                  {t('Ask Mentor')}
                </Link>
                <div className={`forum_header-list_underLine forum_header-list_underLine${isBtnClicked ? '' : '_active'}`} />
              </button>

            </ul>
            <button data-testid="toggle-btn" type="button" onClick={() => dispatch(onOpen())} className={`btn ${AskQuestionClassName}`}>{t('ASK QUESTION')}</button>
          </div>
        </div>
        <div className={scrollClassName}>
          {
            questionsMap.length ? (
              questionsMap.map((val) => (
                <QuestionItem
                  text={val.text}
                  id={val.id}
                  createdDate={val.createdDate}
                  key={val.id}
                  token={idAndToken.token}
                  enrolledCourseId={entity.enrolledCourseId}
                  sectionName={sectionName}
                  responsesMap={responsesMap}
                />
              ))
            ) : (
              <div className="noQuestionsDiv">{t(`No questions. Be the First to Ask a question to ${isBtnClicked ? 'peers' : 'mentor'}!`)}</div>
            )
          }
          {isLoading && <div style={{ marginTop: '40px' }}><Loader /></div>}
          {isLoadingAnswers && <div style={{ marginTop: '40px' }}><Loader /></div>}
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
    </div>
  );
};

export default DiscussionForum;
