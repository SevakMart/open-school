import BookmarkIcon from '../../icons/Bookmark';
import StarIcon from '../../icons/Star';
import { SuggestedCourseType } from '../../types/SuggestedCourseType';
import styles from './LearningPath.module.scss';

const LearningPath = ({
  title, rating, difficulty, keywords,
}:SuggestedCourseType) => {
  const {
    mainContainer, header, mainBody, ratingContent, courseTitle, keywordsContent, difficultyContent,
    ratingValue, keyword,
  } = styles;
  return (
    <div className={mainContainer}>
      <div className={header}>
        <p className={difficultyContent}>{difficulty}</p>
        <p><BookmarkIcon iconSize="1rem" /></p>
      </div>
      <div className={mainBody}>
        <div className={ratingContent}>
          <p><StarIcon /></p>
          <p className={ratingValue}>{rating}</p>
        </div>
        <p className={courseTitle}>{title}</p>
        <div className={keywordsContent}>
          <div className={keyword}>{keywords[0]}</div>
          <div className={keyword}>{keywords[1]}</div>
          <p>
            +
            {keywords.length > 2 ? (keywords.length - 2) : null}
          </p>
        </div>
      </div>
    </div>
  );
};
export default LearningPath;
