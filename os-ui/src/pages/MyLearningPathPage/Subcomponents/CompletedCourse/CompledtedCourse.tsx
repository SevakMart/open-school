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
        <p className={courseTitle}>{title}</p>
      </div>
      <div className={separator} />
      <div className={container}>
        <p className={containerTitle}>Status</p>
        <p className={statusContent}>{courseStatus}</p>
      </div>
      <div className={separator} />
      <div className={container}>
        <p className={containerTitle}>Grade</p>
        <p className={gradeContent}>
          {grade}
          /100
        </p>
      </div>
      <div className={separator} />
      <div className={buttonContainer}>
        <button type="button">Rate Course</button>
      </div>
    </div>
  );
};
export default CompletedCourse;
