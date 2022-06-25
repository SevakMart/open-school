import BookmarkIcon from '../../icons/Bookmark';
import StarIcon from '../../icons/Star';
import { SuggestedCourseType } from '../../types/SuggestedCourseType';
import styles from './LearningPath.module.scss';

const LearningPath = ({
  courseInfo, saveCourse, deleteCourse,
/* eslint-disable-next-line max-len */
}:{courseInfo:SuggestedCourseType, saveCourse?(courseId:number):void, deleteCourse?(courseId:number):void}) => {
  const {
    mainContainer, header, mainBody, ratingContent, courseTitle, keywordsContent, difficultyContent,
    ratingValue, keyword,
  } = styles;
  const handleSaveCourse = (courseId:number) => {
    saveCourse && saveCourse(courseId);
  };
  const handleDeleteCourse = (courseId:number) => {
    deleteCourse && deleteCourse(courseId);
  };
  return (
    <div className={mainContainer}>
      <div className={header}>
        {/* eslint-disable-next-line max-len */}
        <p data-testid={courseInfo.difficulty} className={difficultyContent}>{courseInfo.difficulty}</p>
        <p><BookmarkIcon iconSize="1rem" isBookmarked={courseInfo.isBookMarked} courseId={courseInfo.courseId} saveCourse={handleSaveCourse} deleteCourse={handleDeleteCourse} /></p>
      </div>
      <div className={mainBody}>
        <div className={ratingContent}>
          <p><StarIcon /></p>
          <p data-testid={ratingValue} className={ratingValue}>{courseInfo.rating}</p>
        </div>
        <p data-testid={courseInfo.title} className={courseTitle}>{courseInfo.title}</p>
        <div className={keywordsContent}>
          <div className={keyword}>{courseInfo.keywords[0]}</div>
          <div className={keyword}>{courseInfo.keywords[1]}</div>
          {
           courseInfo.keywords.length > 2 ? (
             <p data-testid={`remainingKeywordNumber of ${courseInfo.title}`}>
               +
               {courseInfo.keywords.length - 2}
             </p>
           ) : null
          }
        </div>
      </div>
    </div>
  );
};
export default LearningPath;
