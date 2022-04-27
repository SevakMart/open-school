import { useState } from 'react';
import { UserCourseType } from '../../../../types/UserCourseType';
import TimeIcon from '../../../../icons/Time';
import CalendarIcon from '../../../../icons/CalenderIcon';
import { transformTime } from '../../../../helpers/TimeTransform';
import styles from './InProgressCourse.module.scss';

const InProgressCourse = ({
  title, courseStatus, percentage, remainingTime, dueDate,
}:Omit<UserCourseType, 'grade'>) => {
  const {
    mainContainer, container, progressBar, buttonContainer, courseTitle,
    progressPercent, OverallProgress, containerTitle, progressing, progressContainer,
    dueDateContent, remainingTimeContent, statusContent, separator,
    remainingTimeContainer, dueDateContainer,
  } = styles;
  const time = useState(transformTime(remainingTime))[0];
  return (
    <div className={mainContainer}>
      <div className={container}>
        <p className={courseTitle}>{title}</p>
        <div className={progressContainer}>
          <div className={progressBar}>
            <div
              className={progressing}
              style={{
                width: `${percentage}%`, backgroundColor: '#A0A3BD', height: '100%', borderRadius: '0.406rem',
              }}
            />
          </div>
          <p className={progressPercent}>
            {percentage}
            %
          </p>
        </div>
        <p className={OverallProgress}>Overall Progress</p>
      </div>
      <div className={separator} />
      <div className={container}>
        <p className={containerTitle}>Remaining Time</p>
        <div className={remainingTimeContainer}>
          <p><TimeIcon /></p>
          <p className={remainingTimeContent}>{time}</p>
        </div>
      </div>
      <div className={separator} />
      <div className={container}>
        <p className={containerTitle}>Status</p>
        <div className={statusContent}>{courseStatus}</div>
      </div>
      <div className={separator} />
      <div className={container}>
        <p className={containerTitle}>Due Date</p>
        <div className={dueDateContainer}>
          <p><CalendarIcon /></p>
          <p className={dueDateContent}>{dueDate}</p>
        </div>
      </div>
      <div className={separator} />
      <div className={buttonContainer}>
        <button type="button">Resume Course</button>
      </div>
    </div>
  );
};
export default InProgressCourse;
