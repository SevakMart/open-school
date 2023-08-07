import React, { useState, useMemo, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';
import { useLocation, useParams } from 'react-router-dom';
import Loader from '../../../component/Loader/Loader';
import { AllQuestions } from '../../../redux/Slices/GetAllQuestionsSlice';
import {
  AllQuestionsMentorFromServer, AllQuestionsPeersFromServer, changeSection, onClose,
} from '../../../redux/Slices/QuestionActionsSlice';
import { RootState } from '../../../redux/Store';
import { CourseDescriptionType } from '../../../types/CourseTypes';
import styles from './DiscussionForum.module.scss';
import { Question, ResponsesMap } from './interfaces/interfaces';
import AskQuestionPopup from './subcomponents/askQuestionPopup/AskQuestionPopup';
import QuestionItem from './subcomponents/QuestionItem/QuestionItem';
import { findSearchedQuestionPeers, clearSearchedQuestions } from '../../../redux/Slices/ForumSearchPeersSlice';
import { findSearchedQuestionMentor, clearSearchedQuestionsMentor } from '../../../redux/Slices/ForumSearchMentorSlice';
import useDebounce from './helpers/useDebounce';
import DiscussionForumHeader from './subcomponents/DiscussionForumHeader/DiscusionForumHeader';
import DiscussionForumSearchBox from './subcomponents/DiscussionForumSearchbox/DiscussinForumSearchbox';
import DiscussionForumShowMoreButton from './subcomponents/DiscussionForumShowMoreButton/DiscussionForumShowMoreButton';

export enum ForumPath {
	ASK_PEERS = 'Ask Peers',
	ASK_MENTOR = 'Ask Mentor'
  }

const DiscussionForum = ({ userInfo }: { userInfo: object }): JSX.Element => {
  const [value, setValue] = useState<string>('');
  const [searchTitle, setSearchTitle] = useState<string>('');
  const [searchTitleInput, setSearchTitleInput] = useState<string>('');
  const [numMatches, setNumMatches] = useState<number>(0);
  const [pageNum, setPageNum] = useState<number>(1);
  const searchStr = useDebounce(searchTitleInput, 500);
  const { courseId } = useParams();
  const location = useLocation();
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const searchQuery = useSelector<RootState, any>((state) => state.GetForumSearchPeers.data);
  const searchQueryMentor = useSelector<RootState, any>((state) => state.GetForumSearchMentor.data);
  const courseDescriptionState = useSelector<RootState>(
		  (state) => state.courseDescriptionRequest,
  ) as { entity: CourseDescriptionType; isLoading: boolean; errorMessage: string };
  const { entity } = courseDescriptionState;
  const AllQuestionsState = useSelector<RootState>((state) => state.QuestionActions) as {
		  questionsWithId: Question[];
		  questionsWithIdToMentor: Question[];
		  isLoading: boolean;
		  isOpen: boolean;
		  section: boolean;
		};
  const AllAnswerState = useSelector<RootState>(
    (state) => state.AnswerActions,
  ) as {
			PeersResponses: ResponsesMap[];
			MentorResponses: ResponsesMap[];
			isLoading: boolean;
		  };
  const AllQuestionFromServer = useSelector<RootState>(
    (state) => state.GetAllQuestions,
  ) as {
			AllquestionsToPeers: Question[];
			AllquestionsToMentor: Question[];
			isLoading: false;
			section: true;
		  };
  const idAndToken = useMemo(
    () => ({
      token: (userInfo as any).token,
      id: (userInfo as any).id,
    }),
    [userInfo],
  );
  const {
    questionsWithId, questionsWithIdToMentor, isLoading, isOpen, section,
  } = AllQuestionsState;
  const { AllquestionsToPeers, AllquestionsToMentor, isLoading: isLoadingAllTheQuestions } = AllQuestionFromServer;
  const { PeersResponses, MentorResponses, isLoading: isLoadingAnswers } = AllAnswerState;
  const {
    inner, container, question_items, num_matches, noQuestionsDiv,
  } = styles;
  const sectionName = section ? 'peers' : 'mentor';
  const currentPath = location.pathname;
  let isBtnClicked = true;
  const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setValue(event.target.value);
  };

  const handleClose = () => {
		  setValue('');
		  dispatch(onClose());
  };

  const cleanTextField = () => {
		  setValue('');
  };

  if (
		  currentPath === `/userCourse/modulOverview/${courseId}/discussionForum/AskPeers`
  ) isBtnClicked = true;
  if (
		  currentPath === `/userCourse/modulOverview/${courseId}/discussionForum/AskMentor`
  ) isBtnClicked = false;

  const questionsToShow: Question[] = useMemo(() => {
		  if (searchTitle && searchTitle.length >= 3) {
      return isBtnClicked ? searchQuery?.content || [] : searchQueryMentor?.content || [];
		  }

		  return isBtnClicked ? questionsWithId : questionsWithIdToMentor;
  }, [searchQuery, searchQueryMentor, searchTitle, questionsWithId, questionsWithIdToMentor, isBtnClicked]);

  const togglesetPageNum = (type: string) => {
		  if (type === 'plus') {
      setPageNum((prev) => prev + 1);
		  }
		  if (type === 'minus') {
      setPageNum((prev) => prev - 1);
		  }
  };

  const responsesMap: ResponsesMap[] = isBtnClicked
		  ? PeersResponses
		  : MentorResponses;

  const [, setPageReloaded] = useState(true);
  useEffect(() => {
    setPageReloaded(true);
  }, []);

  useEffect(() => {
    const fetchAllQuestions = async () => {
      await Promise.all([
        dispatch(AllQuestions({
          enrolledCourseId: entity.enrolledCourseId, token: idAndToken.token, sectionName: 'peers', pageNum,
        })),
        dispatch(AllQuestions({
          enrolledCourseId: entity.enrolledCourseId, token: idAndToken.token, sectionName: 'mentor', pageNum,
        })),
      ]);
      setPageReloaded(false);
    };
    fetchAllQuestions();
  }, [pageNum]);

  useEffect(() => {
    dispatch(AllQuestionsPeersFromServer(AllquestionsToPeers));
    dispatch(AllQuestionsMentorFromServer(AllquestionsToMentor));
  }, [AllquestionsToPeers, AllquestionsToMentor]);

  const handleSearch = (title: string) => {
    if (title.length < 3) {
			  dispatch(clearSearchedQuestions());
			  dispatch(clearSearchedQuestionsMentor());
			  setSearchTitle('');
			  setNumMatches(0);
			  return;
    }

    if (isBtnClicked) {
			  dispatch(
        findSearchedQuestionPeers({
				  enrolledCourseId: entity.enrolledCourseId,
				  pageable: { page: 0, size: 15, q: title },
				  token: idAndToken.token,
        }),
			  );
    } else {
			  dispatch(
        findSearchedQuestionMentor({
				  enrolledCourseId: entity.enrolledCourseId,
				  params: { page: 0, size: 15, q: title },
				  token: idAndToken.token,
        }),
			  );
    }

    setSearchTitle(title);
  };

  useEffect(() => {
    handleSearch(searchTitleInput);
  }, [searchStr]);

  const onChangeSection = (value: boolean) => {
    if (isBtnClicked !== value) {
      dispatch(changeSection(value));
	  setSearchTitleInput('');
    }
  };

  useEffect(() => {
    if (isBtnClicked) {
	  if (searchQuery && searchQuery.content) {
        setNumMatches(searchQuery.content.length);
	  } else {
        setNumMatches(0);
	  }
    } else if (searchQueryMentor && searchQueryMentor.content) {
      setNumMatches(searchQueryMentor.content.length);
	  } else {
      setNumMatches(0);
	  }
  }, [isBtnClicked, searchQuery, searchQueryMentor]);

  const noResultsMessage = useMemo(() => {
    if (searchTitle) {
	  if (isBtnClicked) {
        if (searchQuery && searchQuery.content && searchQuery.content.length === 0) {
		  return <></>;
        }
	  } else if (searchQueryMentor && searchQueryMentor.content && searchQueryMentor.content.length === 0) {
		  return <></>;
      }
    }
    return null;
  }, [searchTitle, searchQuery, searchQueryMentor, isBtnClicked]);

  const showMoreButtonVisible = questionsToShow.length >= 15;
  return (
    <div className={inner}>
      <div className={container}>
        <DiscussionForumHeader isBtnClicked={isBtnClicked} onChangeSection={onChangeSection} courseId={courseId} isLoading={isLoading} />
        <div className={question_items}>
          <DiscussionForumSearchBox setSearchTitleInput={setSearchTitleInput} searchTitleInput={searchTitleInput} />
        </div>
        {searchTitle && (
          <div className={num_matches}>{`${numMatches} matches found`}</div>
        )}
        {questionsToShow.length > 0 || searchQuery.length > 0 ? (
          (pageNum === 1 ? questionsToShow.slice(0, 15) : questionsToShow).map(
            (val) => (
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
                searchQuery={searchTitle}
              />
            ),
          )
        ) : (
          <div className={noQuestionsDiv} data-testid="noQuestionsDiv">
            {noResultsMessage ? (
              ''
            ) : (
              t(
                `No questions. Be the First to Ask a question to ${
                  isBtnClicked ? 'peers' : 'mentor'
                }!`,
              )
            )}
          </div>
        )}
        {isLoading && <div style={{ marginTop: '40px' }}><Loader /></div>}
        {isLoadingAnswers && <div style={{ marginTop: '40px' }}><Loader /></div>}
      </div>
      {showMoreButtonVisible && (
      <DiscussionForumShowMoreButton
        isLoadingAllTheQuestions={isLoadingAllTheQuestions}
        togglesetPageNum={togglesetPageNum}
        pageNum={pageNum}
      />
	  )}
      {isOpen && (
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
      )}
    </div>
  );
};

export default DiscussionForum;
