import { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useLocation, useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { DispatchType } from '../../../../redux/Store';
import { CourseDescriptionType, SuggestedCourseType } from '../../../../types/CourseTypes';
import { getUserSavedCourse } from '../../../../redux/Slices/SavedLearningPathSlice';
import { deleteUserSavedCourse } from '../../../../redux/Slices/DeleteUserSavedCourse';
import userService from '../../../../services/userService';
import BookmarkIcon from '../../../../icons/Bookmark';
import CourseSummaryItem from './Subcomponent/CourseSummaryItem/CourseSummaryItem';
import ShareIcon from '../../../../assets/svg/ShareIcon.svg';
import Button from '../../../../component/Button/Button';
import styles from './CourseSummary.module.scss';

/* eslint-disable max-len */

const CourseSummary = ({
  rating, enrolled, level, language, duration, courseId, userIdAndToken, title,
}:Omit<CourseDescriptionType, 'description'|'goal'|'modules'|'mentorDto'> & {courseId:number, userIdAndToken:{id:number, token:string}}) => {
  const { t } = useTranslation();
  const location = useLocation();
  const navigate = useNavigate();
  const params = new URLSearchParams(location.search);
  const isEnrolled = params.has('enrolled');
  const { id: userId, token } = userIdAndToken;
  const courseSummaryItem = {
    rating, enrolled, level, language, duration,
  };
  const dispatch = useDispatch<DispatchType>();

  const {
    mainContent, headerContent, headerIcons, courseSummaryItemList, buttonContainer, userEnrollText,
    enrolledButtonContainer,
  } = styles;

  const saveCourse = (courseTitle:string, courseId:number) => {
    userService.saveUserPreferredCourses(userId, courseId, token);
    params.set('savedCourse', courseTitle);
    navigate(`${location.pathname}?${params}`, { replace: true });
  };
  const deleteCourse = (courseTitle:string, courseId:number) => {
    dispatch(deleteUserSavedCourse({ userId, courseId, token }));
    params.delete('savedCourse');
    navigate(`${location.pathname}`, { replace: true });
  };

  useEffect(() => {
    dispatch(getUserSavedCourse({ userId, token, params: {} }))
      .unwrap()
      .then((savedCourseList:SuggestedCourseType[]) => {
        if (savedCourseList.some((savedCourse:SuggestedCourseType) => savedCourse.id === courseId)) {
          params.set('savedCourse', savedCourseList.find((savedCourse:SuggestedCourseType) => savedCourse.id === courseId)!.title);
          navigate(`${location.pathname}?${params}`, { replace: true });
        } else {
          params.delete('savedCourse');
          navigate(`${location.pathname}`, { replace: true });
        }
      });
  }, []);

  return (
    <div className={mainContent}>
      <div className={headerContent}>
        <h2>{t('string.courseDescriptionPage.title.summary')}</h2>
        <div className={headerIcons}>
          <img src={ShareIcon} alt="Share icon" />
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
      <div className={courseSummaryItemList}>
        {
          Object.entries(courseSummaryItem).map((item) => (
            <CourseSummaryItem
              key={item[0]}
              title={item[0]}
              value={item[1]}
            />
          ))
        }
      </div>
      {isEnrolled && <p className={userEnrollText}>{t('string.courseDescriptionPage.title.userEnrolled')}</p>}
      <div className={isEnrolled ? enrolledButtonContainer : buttonContainer}>
        {isEnrolled && <Button.MainButton onClick={() => null} className={['courseSummaryButton']}>{t('button.courseDescriptionPage.startCourse') }</Button.MainButton>}
        {!isEnrolled && (
        <Button.EnrollButton
          className={['courseSummaryButton']}
          courseId={courseId}
          userId={userId}
          token={token}
        >
          {t('button.courseDescriptionPage.enrollInCourse')}
        </Button.EnrollButton>
        )}
      </div>
    </div>
  );
};
export default CourseSummary;
