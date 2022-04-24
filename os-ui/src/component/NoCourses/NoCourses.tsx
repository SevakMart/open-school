import NoCoursesIcon from '../../assets/svg/NoCoursesYet.svg';
import { NO_COURSES_YET, EXPLORE_COURSES } from '../../constants/Strings';
import styles from './NoCourses.module.scss';

const NoCourses = () => {
  const { mainContainer } = styles;
  return (
    <div className={mainContainer}>
      <img src={NoCoursesIcon} alt="No Courses Yet" />
      <p>{NO_COURSES_YET}</p>
      <button type="button">{EXPLORE_COURSES}</button>
    </div>
  );
};
export default NoCourses;
