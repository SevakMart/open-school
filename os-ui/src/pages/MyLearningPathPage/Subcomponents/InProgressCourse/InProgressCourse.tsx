import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { ProgressedCourse } from '../../../../types/CourseTypes';
import ClockIcon from '../../../../assets/svg/ClockIcon.svg';
import CalendarIcon from '../../../../assets/svg/CalendarIcon.svg';
import Button from '../../../../component/Button/Button';
import { transformTime } from '../../../../helpers/TimeTransform';
import styles from './InProgressCourse.module.scss';

const InProgressCourse = ({
  title, courseStatus, percentage, remainingTime, dueDate, courseId,
}:ProgressedCourse & { courseId: number }) => {
  const {
    mainContainer, titleContainer, container, progressBar, buttonContainer, courseTitle,
    progressPercent, OverallProgress, containerTitle, progressing, progressContainer,
    dueDateContent, remainingTimeContent, statusContent, separator,
    remainingTimeContainer, dueDateContainer, calenderIcon, timeIcon,
  } = styles;
  const { t } = useTranslation();
  const navigate = useNavigate();
  const time = useState(transformTime(remainingTime))[0];
  return (
    <div className={mainContainer}>
      <div className={titleContainer}>
        <p data-testid={title} className={courseTitle}>{title}</p>
        <div className={progressContainer}>
          <div className={progressBar}>
            <div
              className={progressing}
              style={{ width: `${percentage}%` }}
            />
          </div>
          <p data-testid={percentage} className={progressPercent}>
            {percentage}
            %
          </p>
        </div>
        <p className={OverallProgress}>{t('string.myLearningPaths.inProgressCourse.overallProgress')}</p>
      </div>
      <div className={separator} />
      <div className={container}>
        <p className={containerTitle}>{t('string.myLearningPaths.inProgressCourse.remainingTime')}</p>
        <div className={remainingTimeContainer}>
          <img src={ClockIcon} className={timeIcon} alt="clockIcon" />
          <p data-testid={remainingTime} className={remainingTimeContent}>{time}</p>
        </div>
      </div>
      <div className={separator} />
      <div className={container}>
        <p className={containerTitle}>{t('string.myLearningPaths.inProgressCourse.status')}</p>
        <p data-testid={courseStatus} className={statusContent}>{courseStatus}</p>
      </div>
      <div className={separator} />
      <div className={container}>
        <p className={containerTitle}>{t('string.myLearningPaths.inProgressCourse.dueDate')}</p>
        <div className={dueDateContainer}>
          <img src={CalendarIcon} className={calenderIcon} alt="calendarIcon" />
          <p data-testid={dueDate} className={dueDateContent}>{dueDate}</p>
        </div>
      </div>
      <div className={separator} />
      <div className={buttonContainer}>
        <Button.MainButton className={['resumeCourse']} onClick={() => navigate(`/userCourse/modulOverview/${courseId}`)}>
          {t('button.myLearningPathsPage.resumeCourse')}
        </Button.MainButton>
      </div>
    </div>
  );
};
export default InProgressCourse;
