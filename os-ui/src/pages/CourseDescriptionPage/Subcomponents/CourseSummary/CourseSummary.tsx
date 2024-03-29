import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { DispatchType } from '../../../../redux/Store';
import { CourseDescriptionType } from '../../../../types/CourseTypes';
import { getUserSavedCourse } from '../../../../redux/Slices/SavedLearningPathSlice';
import { deleteUserSavedCourse } from '../../../../redux/Slices/DeleteUserSavedCourseSlice';
import { saveUserPreferredCourse } from '../../../../redux/Slices/SaveUserPreferredCourseSlice';
import BookmarkIcon from '../../../../icons/Bookmark';
import CourseSummaryItem from './Subcomponent/CourseSummaryItem/CourseSummaryItem';
import Button from '../../../../component/Button/Button';
import styles from './CourseSummary.module.scss';
import ShareButton from '../../../../icons/ShareIcon/ShareIcon';

const CourseSummary = ({
  Rating,
  Enrolled,
  Course_Level,
  Language,
  Estimated_efforts,
  courseId,
  userIdAndToken,
  title,
  currentUserEnrolled,
}: Omit<CourseDescriptionType, 'description' | 'goal' | 'modules' | 'mentorDto'> & {
  courseId: number;
  userIdAndToken: { id: number; token: string };

}) => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { id: userId, token } = userIdAndToken;
  const dispatch = useDispatch<DispatchType>();

  const [enrolledInCourse, setEnrolledInCourse] = useState<boolean>(Boolean(currentUserEnrolled));
  const [enrollButtonDisabled, setEnrollButtonDisabled] = useState<boolean>(false);
  const [courseSummaryItem, setCourseSummaryItem] = useState({
    Rating,
    Enrolled,
    CourseLevel: Course_Level,
    Language,
    EstimatedEfforts: Estimated_efforts,
  });

  const {
    mainContent, headerContent, headerIcons, courseSummaryItemList, buttonContainer, userEnrollText,
    enrolledButtonContainer, enrolledHeaderContent, enrolledCourseSummaryItemList, enrolledMainContent, enrollText,
  } = styles;

  const saveCourse = (courseTitle:string, courseId:number) => {
    dispatch(saveUserPreferredCourse({ userId, courseId, token }));
  };

  const deleteCourse = (courseTitle:string, courseId:number) => {
    dispatch(deleteUserSavedCourse({ userId, courseId, token }));
  };

  useEffect(() => {
    setEnrolledInCourse(Boolean(currentUserEnrolled));
    dispatch(getUserSavedCourse({ userId, token, params: {} }));
  }, []);

  const handleEnrollButtonClick = () => {
    setEnrolledInCourse(true);
    setEnrollButtonDisabled(true);
    setCourseSummaryItem((prevState) => ({
      ...prevState,
      enrolled: prevState.Enrolled + 1,
    }));
  };

  return (
    <div className={currentUserEnrolled ? enrolledMainContent : mainContent}>
      <div className={currentUserEnrolled ? enrolledHeaderContent : headerContent}>
        <h2>{t('string.courseDescriptionPage.title.summary')}</h2>
        <div className={headerIcons}>
          <ShareButton courseId={courseId} />
          <BookmarkIcon
            iconSize="20px"
            courseTitle={title}
            courseId={courseId}
            saveCourse={saveCourse}
            deleteCourse={deleteCourse}
            isCourseSummaryBookmarkIcon
          />
        </div>
      </div>

      <div className={currentUserEnrolled ? enrolledCourseSummaryItemList : courseSummaryItemList}>
        {
		Object.entries(courseSummaryItem).map((item) => (
  <CourseSummaryItem
    key={item[0]}
    title={item[0].replace(/_/g, ' ')}
    value={item[1]}
  />
		))
		}
      </div>
      {enrolledInCourse && (
      <p className={currentUserEnrolled ? userEnrollText : enrollText}>
        {t('string.courseDescriptionPage.title.userEnrolled')}
      </p>
	  )}
      <div className={enrolledInCourse ? enrolledButtonContainer : buttonContainer}>
        {enrolledInCourse ? (
          <Button.MainButton
            onClick={() => navigate(`/userCourse/modulOverview/${courseId}`)}
            className={['courseSummaryButton']}
          >
            {t('button.courseDescriptionPage.startCourse')}
          </Button.MainButton>
        ) : (
          <Button.EnrollButton
            className={['courseSummaryButton']}
            courseId={courseId}
            userId={userId}
            token={token}
            onEnroll={handleEnrollButtonClick}
            disabled={enrollButtonDisabled}
          >
            {t('button.courseDescriptionPage.enrollInCourse')}
          </Button.EnrollButton>
        )}
      </div>
    </div>
  );
};
export default CourseSummary;
