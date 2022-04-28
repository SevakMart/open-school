import { GRADE, RATE_COURSE, STATUS } from '../../../../constants/Strings';
import { UserCourseType } from '../../../../types/UserCourseType';
import styles from './CompletedCourse.module.scss';

const CompletedCourse = ({ title, courseStatus, grade }:Pick<UserCourseType, 'title'|'courseStatus'|'grade'>) => {
  const {
    separator, mainContainer, container, courseTitle,
    containerTitle, statusContent, gradeContent, buttonContainer,
  } = styles;
  return (
    <div className={mainContainer}>
      <div className={container}>
        <p data-testid={title} className={courseTitle}>{title}</p>
      </div>
      <div className={separator} />
      <div className={container}>
        <p className={containerTitle}>{STATUS}</p>
        <p data-testid={courseStatus} className={statusContent}>{courseStatus}</p>
      </div>
      <div className={separator} />
      <div className={container}>
        <p className={containerTitle}>{GRADE}</p>
        <p data-testid={grade} className={gradeContent}>
          {grade}
          /100
        </p>
      </div>
      <div className={separator} />
      <div className={buttonContainer}>
        <button type="button">{RATE_COURSE}</button>
      </div>
    </div>
  );
};
export default CompletedCourse;
