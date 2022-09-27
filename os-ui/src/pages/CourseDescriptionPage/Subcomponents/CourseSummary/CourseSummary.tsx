import { useTranslation } from 'react-i18next';
import { CourseDescriptionType } from '../../../../types/CourseDescriptionType';
import BookmarkIcon from '../../../../icons/Bookmark';
import ShareIcon from '../../../../icons/Share';
import CourseSummaryItem from './Subcomponent/CourseSummaryItem/CourseSummaryItem';
import { SuggestedCourseType } from '../../../../types/SuggestedCourseType';
import { useCheck } from '../../../../custom-hooks/useCheck';
import Button from '../../../../component/Button/Button';
import styles from './CourseSummary.module.scss';

type CourseListType=SuggestedCourseType & {id:number, isBookMarked?:boolean}

const CourseSummary = ({
  rating, enrolled, level, language, duration, courseId, userIdAndToken,
}:Omit<CourseDescriptionType, 'title'|'description'|'goal'|'modules'|'mentorDto'> & {courseId:number, userIdAndToken:{id:number, token:string}}) => {
  const { t } = useTranslation();
  const [isChecked] = useCheck('enrolled', 'true');
  const { id: userId, token } = userIdAndToken;
  const courseSummaryItem = {
    rating, enrolled, level, language, duration,
  };
  const {
    mainContent, headerContent, headerIcons, courseSummaryItemList, buttonContainer,
  } = styles;
  /* const saveUserCourse = (courseId:number) => {
    userService.saveUserPreferredCourses(userIdAndToken.id, courseId, userIdAndToken.token);
    setIsBookmarked(true);
  };
  const deleteUserSavedCourse = (courseId:number) => {
    userService.deleteUserSavedCourses(userIdAndToken.id, courseId, userIdAndToken.token);
    setIsBookmarked(false);
  }; */
  /* useEffect(() => {
    userService.getUserSavedCourses(userIdAndToken.id, userIdAndToken.token, { page: 0, size: 100 })
      .then((data) => {
        setIsBookmarked(data.content.some((savedCourse:CourseListType) => savedCourse.id === courseId));
      });
  }, []); */

  return (
    <div className={mainContent}>
      <div className={headerContent}>
        <h2>{t('string.courseDescription.title.summary')}</h2>
        <div className={headerIcons}>
          <ShareIcon iconSize="0.5 rem" />
          <BookmarkIcon
            iconSize="0.5 rem"
            courseId={courseId}
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
      {isChecked && <p>{t('string.courseDescription.title.userEnrolled')}</p>}
      <div className={buttonContainer}>
        {isChecked && <Button.MainButton onClick={() => null} className={['courseSummaryButton']}>{t('button.startLearning') }</Button.MainButton>}
        {!isChecked && (
        <Button.EnrollButton
          className={['courseSummaryButton']}
          courseId={courseId}
          userId={userId}
          token={token}
        >
          {t('button.enroll')}
        </Button.EnrollButton>
        )}
      </div>
    </div>
  );
};
export default CourseSummary;
