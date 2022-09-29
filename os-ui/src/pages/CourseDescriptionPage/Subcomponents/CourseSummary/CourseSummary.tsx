import { useTranslation } from 'react-i18next';
import { useLocation } from 'react-router-dom';
import { CourseDescriptionType } from '../../../../types/CourseTypes';
import BookmarkIcon from '../../../../icons/Bookmark';
import CourseSummaryItem from './Subcomponent/CourseSummaryItem/CourseSummaryItem';
import ShareIcon from '../../../../assets/svg/ShareIcon.svg';
import Button from '../../../../component/Button/Button';
import styles from './CourseSummary.module.scss';

const CourseSummary = ({
  rating, enrolled, level, language, duration, courseId, userIdAndToken,
}:Omit<CourseDescriptionType, 'title'|'description'|'goal'|'modules'|'mentorDto'> & {courseId:number, userIdAndToken:{id:number, token:string}}) => {
  const { t } = useTranslation();
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const isEnrolled = params.has('enrolled');
  const { id: userId, token } = userIdAndToken;
  const courseSummaryItem = {
    rating, enrolled, level, language, duration,
  };

  const {
    mainContent, headerContent, headerIcons, courseSummaryItemList, buttonContainer, userEnrollText,
    enrolledButtonContainer,
  } = styles;

  return (
    <div className={mainContent}>
      <div className={headerContent}>
        <h2>{t('string.courseDescriptionPage.title.summary')}</h2>
        <div className={headerIcons}>
          <img src={ShareIcon} alt="Share icon" />
          <BookmarkIcon iconSize="20px" courseId={courseId} />
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
