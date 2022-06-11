import BookmarkIcon from '../../icons/Bookmark';
import StarIcon from '../../icons/Star';
import { SuggestedCourseType } from '../../types/SuggestedCourseType';
import styles from './LearningPath.module.scss';

const LearningPath = ({
  title, rating, difficulty, keywords, isBookMarked, courseId, saveCourse, deleteCourse,
}:SuggestedCourseType) => {
  const {
    mainContainer, header, mainBody, ratingContent, courseTitle, keywordsContent, difficultyContent,
    ratingValue, keyword,
  } = styles;
  /* const handleBookMarkModification = (courseId?:number) => {
    handleModification && handleModification(courseId);
  }; */
  const handleSaveCourse = (courseId:number) => {
    saveCourse && saveCourse(courseId);
  };
  const handleDeleteCourse = (courseId:number) => {
    deleteCourse && deleteCourse(courseId);
  };
  return (
    <div className={mainContainer}>
      <div className={header}>
        <p data-testid={difficulty} className={difficultyContent}>{difficulty}</p>
        <p><BookmarkIcon iconSize="1rem" isBookmarked={isBookMarked} courseId={courseId} saveCourse={handleSaveCourse} deleteCourse={handleDeleteCourse} /></p>
      </div>
      <div className={mainBody}>
        <div className={ratingContent}>
          <p><StarIcon /></p>
          <p data-testid={ratingValue} className={ratingValue}>{rating}</p>
        </div>
        <p data-testid={title} className={courseTitle}>{title}</p>
        <div className={keywordsContent}>
          <div className={keyword}>{keywords[0]}</div>
          <div className={keyword}>{keywords[1]}</div>
          {
           keywords.length > 2 ? (
             <p data-testid={`remainingKeywordNumber of ${title}`}>
               +
               {keywords.length - 2}
             </p>
           ) : null
          }
        </div>
      </div>
    </div>
  );
};
export default LearningPath;
