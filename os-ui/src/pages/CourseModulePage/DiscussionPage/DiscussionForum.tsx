import { useState, useMemo, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useLocation, useParams } from 'react-router-dom';
import Loader from '../../../component/Loader/Loader';
import { AllQuestions } from '../../../redux/Slices/GetAllQuestionsSlice';
import {
  AllQuestionsFromServer, changeSection, onClose, onOpen,
} from '../../../redux/Slices/QuestionActionsSlice';
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

  // get allQuestions from server
  const AllQuestionFromServer = useSelector<RootState>((state) => state.GetAllQuestions) as {
    AllquestionsToPeers: Question[],
    AllquestionsToMentor: Question[],
    isLoading: false,
    section: true,
  };

  const { AllquestionsToPeers, AllquestionsToMentor } = AllQuestionFromServer;

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
    dispatch(changeSection(isBtnClicked));
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

  // get all questions
  const AllQuestionsFromServerMap = isBtnClicked ? AllquestionsToPeers : AllquestionsToMentor;
  const [isPageReloaded, setPageReloaded] = useState(true);

  useEffect(() => {
    setPageReloaded(true);
  }, []);

  useEffect(() => {
    const fetchAllQuestions = async () => {
      try {
        await Promise.all([
          dispatch(AllQuestions({ enrolledCourseId: entity.enrolledCourseId, token: idAndToken.token, sectionName: 'peers' })),
          dispatch(AllQuestions({ enrolledCourseId: entity.enrolledCourseId, token: idAndToken.token, sectionName: 'mentor' })),
        ]);
        setPageReloaded(false);
      } catch (error) {
        throw new Error('something went wrong');
      }
    };
    fetchAllQuestions();
  }, []);

  useEffect(() => {
    dispatch(AllQuestionsFromServer(AllQuestionsFromServerMap));
  }, [isPageReloaded, isBtnClicked]);

  // change Section
  const onChangeSection = (value:boolean) => {
    dispatch(changeSection(value));
  };

  return (
    <div className="inner">
      <div className="container">
        <div className="forum_header">
          <h1 className="forum_header-title">{t('Discussion Forum')}</h1>
          <div className="forum_header-inner">
            <ul className="forum_header-menu">
              <button disabled={isLoading} type="button" className="forum_header-list forum_header-list" onClick={() => onChangeSection(true)}>
                <Link to={`/userCourse/modulOverview/${courseId}/discussionForum/AskPeers`} className={`forum_header-list_link forum_header-list_link${isBtnClicked ? '_active' : ''}`}>
                  {t('Ask Peers')}
                </Link>
                <div className={`forum_header-list_underLine forum_header-list_underLine${isBtnClicked ? '_active' : ''}`} />
              </button>
              <button disabled={isLoading} type="button" className="forum_header-list forum_header-list" onClick={() => onChangeSection(false)}>
                <Link to={`/userCourse/modulOverview/${courseId}/discussionForum/AskMentor`} className={`forum_header-list_link forum_header-list_link${isBtnClicked ? '' : '_active'}`}>
                  {t('Ask Mentor')}
                </Link>
                <div className={`forum_header-list_underLine forum_header-list_underLine${isBtnClicked ? '' : '_active'}`} />
              </button>

            </ul>
            <button data-testid="toggle-btn" type="button" onClick={() => dispatch(onOpen())} className="btn">{t('ASK QUESTION')}</button>
          </div>
        </div>
        <div className="">
          {
            questionsMap.length ? (
              questionsMap.map((val) => (
                <QuestionItem
                  text={val.text}
                  id={val.id}
                  createdDate={val.createdDate}
                  name={val.name}
                  surname={val.surname}
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
