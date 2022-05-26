import { useEffect, useState } from 'react';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import FilterComponent from './Subcomponents/FilterComponent/FilterComponent';
import {
  ALL_LEARNING_PATHS, FILTER, LAST_INSERTED, RATING, SAVED_LEARNING_PATHS, SORT_BY,
} from '../../constants/Strings';
import styles from './AllLearningPathPage.module.scss';

const AllLearningPathPage = () => {
  const [isVisible, setIsVisible] = useState(true);
  const {
    mainContainer, learningPathsMainContainer,
    learningPathsHeader, sortingContainer,
  } = styles;

  const changeVisibility = () => {
    setIsVisible((prevState) => !prevState);
  };

  return (
    <>
      <NavbarOnSignIn />
      <div className={mainContainer}>
        <FilterComponent changeVisibility={changeVisibility} />
        <div className={learningPathsMainContainer} style={isVisible ? { width: '75%', transitionDuration: '2s' } : { width: '98%', transitionDuration: '2s' }}>
          <div className={learningPathsHeader}>
            <nav>
              <p>{ALL_LEARNING_PATHS}</p>
              <p>{SAVED_LEARNING_PATHS}</p>
            </nav>
            <div className={sortingContainer}>
              <label htmlFor="sorting">{SORT_BY}</label>
              <select name="sorting" id="sorting">
                <option value="Rating">{RATING}</option>
                <option value="LastInserted">{LAST_INSERTED}</option>
              </select>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
export default AllLearningPathPage;
