import { useTranslation } from 'react-i18next';
import { UserCourseType } from '../../../../types/UserCourseType';
import Button from '../../../../component/Button/Button';
import styles from './CompletedCourse.module.scss';

const CompletedCourse = ({ title, courseStatus, grade }:Pick<UserCourseType, 'title'|'courseStatus'|'grade'>) => {
  const {
    separator, mainContainer, container, courseTitle,
    containerTitle, statusContent, gradeContent, buttonContainer,
  } = styles;
  const { t } = useTranslation();
  return (
    <div className={mainContainer}>
      <p data-testid={title} className={courseTitle}>{title}</p>
      <div className={separator} />
      <div className={container}>
        <p className={containerTitle}>{t('string.myLearningPaths.completedCourse.status')}</p>
        <p data-testid={courseStatus} className={statusContent}>{courseStatus}</p>
      </div>
      <div className={separator} />
      <div className={container}>
        <p className={containerTitle}>{t('string.myLearningPaths.completedCourse.grade')}</p>
        <p data-testid={grade} className={gradeContent}>
          {grade}
          /100
        </p>
      </div>
      <div className={separator} />
      <div className={buttonContainer}>
        <Button.MainButton className={['rateCourse']} onClick={() => null}>
          {t('string.myLearningPaths.completedCourse.rateCourse')}
        </Button.MainButton>
      </div>
    </div>
  );
};
export default CompletedCourse;
