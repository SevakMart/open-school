import { useNavigate } from 'react-router-dom';
import BookmarkIcon from '../../icons/Bookmark';
import StarIcon from '../../icons/Star';
import { SuggestedCourseType } from '../../types/CourseTypes';
import styles from './LearningPath.module.scss';
/* eslint-disable max-len */
const LearningPath = ({ courseInfo }:{courseInfo:SuggestedCourseType}) => {
  const {
    mainContainer, header, mainBody, ratingContent, courseTitle, keywordsContent, difficultyContent,
    ratingValue, keyword, bookmarkIcon, iconStyle,
  } = styles;
  const navigate = useNavigate();

  const headToCourseDescriptionPage = () => {
    navigate(`/userCourse/${courseInfo.id}`);
  };

  return (
    <div className={mainContainer}>
      <div className={header}>
        {/* eslint-disable-next-line max-len */}
        <p data-testid={courseInfo.difficulty} className={difficultyContent}>{courseInfo.difficulty}</p>
        <p className={bookmarkIcon}><BookmarkIcon iconSize="1rem" courseId={courseInfo.id} courseTitle={courseInfo.title} /></p>
      </div>
      <div className={mainBody} onClick={headToCourseDescriptionPage}>
        <div className={ratingContent}>
          <p className={iconStyle}><StarIcon /></p>
          <p data-testid={ratingValue} className={ratingValue}>{courseInfo.rating}</p>
        </div>
        <p data-testid={courseInfo.title} className={courseTitle}>{courseInfo.title}</p>
        <div className={keywordsContent}>
          <div className={keyword}>{courseInfo.keywords[0]}</div>
          <div className={keyword}>{courseInfo.keywords[1]}</div>
          {courseInfo.keywords.length > 2 && (
          <p data-testid={`remainingKeywordNumber of ${courseInfo.title}`}>
            +
            {courseInfo.keywords.length - 2}
          </p>
          )}
        </div>
      </div>
    </div>
  );
};
export default LearningPath;
