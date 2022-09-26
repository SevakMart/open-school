import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { CourseDescriptionType } from '../../../../types/CourseDescriptionType';
import BookmarkIcon from '../../../../icons/Bookmark';
import ShareIcon from '../../../../icons/Share';
import CourseSummaryItem from './Subcomponent/CourseSummaryItem/CourseSummaryItem';
import userService from '../../../../services/userService';
import { SuggestedCourseType } from '../../../../types/SuggestedCourseType';
import styles from './CourseSummary.module.scss';

type CourseListType=SuggestedCourseType & {id:number, isBookMarked?:boolean}

const CourseSummary = ({
  rating, enrolled, level, language, duration, enrollInCourse, isEnrolled, courseId, userIdAndToken,
}:Omit<CourseDescriptionType, 'title'|'description'|'goal'|'modules'|'mentorDto'> & {enrollInCourse:()=>void, isEnrolled:boolean, courseId:number, userIdAndToken:{id:number, token:string}}) => {
  const { t } = useTranslation();
  const [isBookmarked, setIsBookmarked] = useState(false);
  const courseSummaryItem = {
    rating, enrolled, level, language, duration,
  };
  const {
    mainContent, headerContent, headerIcons, courseSummaryItemList, buttonContainer,
  } = styles;
  const saveUserCourse = (courseId:number) => {
    userService.saveUserPreferredCourses(userIdAndToken.id, courseId, userIdAndToken.token);
    setIsBookmarked(true);
  };
  const deleteUserSavedCourse = (courseId:number) => {
    userService.deleteUserSavedCourses(userIdAndToken.id, courseId, userIdAndToken.token);
    setIsBookmarked(false);
  };
  useEffect(() => {
    userService.getUserSavedCourses(userIdAndToken.id, userIdAndToken.token, { page: 0, size: 100 })
      .then((data) => {
        /* eslint-disable-next-line max-len */
        setIsBookmarked(data.content.some((savedCourse:CourseListType) => savedCourse.id === courseId));
      });
  }, []);

  return (
    <div className={mainContent}>
      <div className={headerContent}>
        <h2>{t('string.courseDescription.title.summary')}</h2>
        <div className={headerIcons}>
          <ShareIcon iconSize="0.5 rem" />
          {/* <BookmarkIcon
            iconSize="0.5 rem"
            courseId={courseId}
            saveCourse={saveUserCourse}
            deleteCourse={deleteUserSavedCourse}
  /> */}
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
      <div className={buttonContainer}>
        {isEnrolled && <p>{t('string.courseDescription.title.userEnrolled')}</p>}
        <button type="button" onClick={() => enrollInCourse()}>{isEnrolled ? t('button.startLearning') : t('button.enroll')}</button>
      </div>
    </div>
  );
};
export default CourseSummary;
