import FilterComponent from '../FilterComponent/FilterComponent';
import CourseContentHeader from '../CourseCountentHeader/CourseContentHeader';
import styles from './MainContent.module.scss';

const MainContent = () => {
  // const {} = styles;
  const a = 1;
  return (
    <div>
      <FilterComponent />
      <div>
        <CourseContentHeader />
      </div>
    </div>
  );
};
export default MainContent;
