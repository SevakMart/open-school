import { useTranslation } from 'react-i18next';
import { CourseDescriptionType } from '../../../../types/CourseDescriptionType';
import BookmarkIcon from '../../../../icons/Bookmark';
import ShareIcon from '../../../../icons/Share';
import CourseSummaryItem from './Subcomponent/CourseSummaryItem/CourseSummaryItem';
import styles from './CourseSummary.module.scss';

const CourseSummary = ({
  rating, enrolled, level, language, duration, enrollInCourse, isEnrolled,
}:Omit<CourseDescriptionType, 'title'|'description'|'goal'|'modules'|'mentorDto'> & {enrollInCourse:()=>void, isEnrolled:boolean}) => {
  const { t } = useTranslation();
  const courseSummaryItem = {
    rating, enrolled, level, language, duration,
  };
  const {
    mainContent, headerContent, headerIcons, courseSummaryItemList, buttonContainer,
  } = styles;
  return (
    <div className={mainContent}>
      <div className={headerContent}>
        <h2>{t('string.courseDescription.title.summary')}</h2>
        <div className={headerIcons}>
          <ShareIcon iconSize="0.5 rem" />
          <BookmarkIcon iconSize="0.5 rem" />
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
