import { useState } from 'react';
import FilterComponent from '../FilterComponent/FilterComponent';
import SavedCoursesContent from '../SavedCoursesContent/SavedCoursesContent';
import LearningPathCoreContent from '../LearningPathContent/Subcomponents/LearningPathCoreContent/LearningPathCoreContent';
import CourseContentHeader, { HeaderPath } from '../CourseCountentHeader/CourseContentHeader';

import styles from './MainContent.module.scss';
/* eslint-disable max-len */

const MainContent = () => {
  const [focusedHeader, setFocusedHeader] = useState(HeaderPath.ALL_LEARNING_PATHS);

  return (
    <div>
      <FilterComponent />
      <div>
        <CourseContentHeader handleChangeHeader={(headerTitle) => setFocusedHeader(headerTitle)} />
        {focusedHeader === HeaderPath.ALL_LEARNING_PATHS ? <LearningPathCoreContent /> : <SavedCoursesContent />}
      </div>
    </div>
  );
};
export default MainContent;
