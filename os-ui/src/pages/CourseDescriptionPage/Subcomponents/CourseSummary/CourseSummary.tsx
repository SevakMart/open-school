import { useState, useEffect } from 'react';
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
	rating,
	enrolled,
	level,
	language,
	duration,
	courseId,
	userIdAndToken,
	title,
	
  }: Omit<CourseDescriptionType, 'description' | 'goal' | 'modules' | 'mentorDto'> & {
	courseId: number;
	userIdAndToken: { id: number; token: string };
	
  }) => {
	const { t } = useTranslation();
	const location = useLocation();
	const navigate = useNavigate();
	const params = new URLSearchParams(location.search);
	const { id: userId, token } = userIdAndToken;
	const courseSummaryItem = {
	  rating,
	  enrolled,
	  level,
	  language,
	  duration,
	};
	const dispatch = useDispatch<DispatchType>();
    
	const [enrolledInCourse, setEnrolledInCourse] = useState<boolean>(!!enrolled);
	const [enrollButtonDisabled, setEnrollButtonDisabled] = useState<boolean>(false);
  
	const {
	  mainContent,
	  headerContent,
	  headerIcons,
	  courseSummaryItemList,
	  buttonContainer,
	  userEnrollText,
	  enrolledButtonContainer,
	} = styles;
  
	const saveCourse = (courseTitle: string, courseId: number) => {
	  userService.saveUserPreferredCourses(userId, courseId, token);
	  params.set('savedCourse', courseTitle);
	  navigate(`${location.pathname}?${params}`, { replace: true });
	};
	const deleteCourse = (courseTitle: string, courseId: number) => {
	  dispatch(deleteUserSavedCourse({ userId, courseId, token }));
	  params.delete('savedCourse');
	  navigate(`${location.pathname}`, { replace: true });
	};
  
	useEffect(() => {
	  dispatch(getUserSavedCourse({ userId, token, params: {} }))
		.unwrap()
		.then((savedCourseList: SuggestedCourseType[]) => {
		  if (savedCourseList.some((savedCourse: SuggestedCourseType) => savedCourse.id === courseId)) {
			params.set(
			  'savedCourse',
			  savedCourseList.find((savedCourse: SuggestedCourseType) => savedCourse.id === courseId)!.title,
			);
			navigate(`${location.pathname}?${params}`, { replace: true });
		  } else {
			params.delete('savedCourse');
			navigate(`${location.pathname}`, { replace: true });
		  }
		});
	}, []);
  
	const handleEnrollButtonClick = () => {
	  setEnrolledInCourse(true);
	  setEnrollButtonDisabled(true);
	};
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
      {enrolled && <p className={userEnrollText}>{t('string.courseDescriptionPage.title.userEnrolled')}</p>}
      <div className={enrolled ? enrolledButtonContainer : buttonContainer}>
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
