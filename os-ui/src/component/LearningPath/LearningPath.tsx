import { useNavigate } from 'react-router-dom';
import BookmarkIcon from '../../icons/Bookmark';
import StarIcon from '../../icons/Star';
import { SuggestedCourseType } from '../../types/CourseTypes';
import styles from './LearningPath.module.scss';

const LearningPath = ({ courseInfo, saveCourse, deleteCourse }:
  {courseInfo:SuggestedCourseType, saveCourse?:(courseTitle:string, courseId:number)=>void, deleteCourse?:(courseTitle:string, courseId:number)=>void, }) => {
  const {
    mainContainer, header, mainBody, ratingContent, courseTitle, keywordsContent, difficultyContent,
    ratingValue, keyword, bookmarkIcon, iconStyle,
  } = styles;
  const navigate = useNavigate();

  const headToCourseDescriptionPage = () => {
    navigate(`/userCourse/${courseInfo.id}`);
  };

  const handleBookmarkClick = (event: React.MouseEvent<HTMLDivElement>) => {
    event.stopPropagation();
  };

  return (
    <div className={mainContainer}>
      <div className={header} onClick={headToCourseDescriptionPage}>
        <p data-testid={courseInfo.difficulty} className={difficultyContent}>{courseInfo.difficulty}</p>
        <p className={bookmarkIcon} onClick={handleBookmarkClick}>
          <BookmarkIcon
            iconSize="1rem"
            courseId={courseInfo.id}
            courseTitle={courseInfo.title}
            saveCourse={(courseTitle:string, courseId:number) => saveCourse && saveCourse(courseTitle, courseId)}
            deleteCourse={(courseTitle:string, courseId:number) => deleteCourse && deleteCourse(courseTitle, courseId)}
          />
        </p>
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
