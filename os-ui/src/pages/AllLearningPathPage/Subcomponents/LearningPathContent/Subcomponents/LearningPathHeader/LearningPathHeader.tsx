import { useState } from 'react';
import {
  ALL_LEARNING_PATHS, SAVED_LEARNING_PATHS, SORT_BY, LAST_INSERTED, RATING,
} from '../../../../../../constants/Strings';
import styles from './LearningPathHeader.module.scss';

enum LearningPathNav {
  AllLearningPath='AllLearningPath',
  SavedLearningPath='SavedLearningPath'
}

const LearningPathHeader = () => {
  const [activeNavType, setActiveNavType] = useState(LearningPathNav.AllLearningPath);
  const {
    learningPathsHeader, sortingContainer, activeNav, nonActiveNav,
  } = styles;
  return (
    <div className={learningPathsHeader}>
      <nav>
        <p className={activeNavType === 'AllLearningPath' ? activeNav : nonActiveNav}>{ALL_LEARNING_PATHS}</p>
        <p className={activeNavType === 'SavedLearningPath' ? activeNav : nonActiveNav}>{SAVED_LEARNING_PATHS}</p>
      </nav>
      <div className={sortingContainer}>
        <label htmlFor="sorting">{SORT_BY}</label>
        <select name="sorting" id="sorting">
          <option value="Rating">{RATING}</option>
          <option value="LastInserted">{LAST_INSERTED}</option>
        </select>
      </div>
    </div>
  );
};
export default LearningPathHeader;
