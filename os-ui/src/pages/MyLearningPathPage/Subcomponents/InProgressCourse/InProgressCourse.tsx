import { useEffect, useMemo, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getCourseDescription } from '../../../../redux/Slices/CourseDescriptionRequestSlice';
import { RootState } from '../../../../redux/Store';
import { CourseDescriptionType, ProgressedCourse } from '../../../../types/CourseTypes';
import ClockIcon from '../../../../assets/svg/ClockIcon.svg';
import CalendarIcon from '../../../../assets/svg/CalendarIcon.svg';
import Button from '../../../../component/Button/Button';
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
  const userInfoState = useSelector<RootState>((state) => state.userInfo);
  const { userInfo } = userInfoState as any;
  const { t } = useTranslation();
  const navigate = useNavigate();
  const idAndToken = useMemo(() => ({
    token: (userInfo as any)?.token,
    id: (userInfo as any)?.id,
  }), []);

  const courseDescriptionState = useSelector<RootState>((state) => state.courseDescriptionRequest) as { entity: CourseDescriptionType };
  const { entity } = courseDescriptionState;
  const [value, setValue] = useState<string>(entity?.modules?.[0]?.title || '');
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(getCourseDescription({
      courseId: Number(courseId), token: idAndToken.token,
    }));
  }, []);

  const handleChangeValue = () => {
    navigate(`/userCourse/modulOverview/${courseId}`);
    setValue(value);
  };

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
          <p data-testid={remainingTime} className={remainingTimeContent}>{t(`${remainingTime} minutes`)}</p>
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
        <Button.MainButton className={['resumeCourse']} onClick={handleChangeValue}>
          {t('button.myLearningPathsPage.resumeCourse')}
        </Button.MainButton>
      </div>
    </div>
  );
};
export default InProgressCourse;
