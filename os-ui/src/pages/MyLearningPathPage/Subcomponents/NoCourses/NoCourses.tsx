import { useTranslation } from 'react-i18next';
import NoCoursesIcon from '../../../../assets/svg/NoCoursesYet.svg';
import styles from './NoCourses.module.scss';

const NoCourses = () => {
  const { t } = useTranslation();
  const { mainContainer } = styles;
  return (
    <div className={mainContainer}>
      <img src={NoCoursesIcon} alt="No Courses Yet" />
      <p data-testid="No courses yet">{t('string.myLearningPaths.noCourseStatus')}</p>
    </div>
  );
};
export default NoCourses;
