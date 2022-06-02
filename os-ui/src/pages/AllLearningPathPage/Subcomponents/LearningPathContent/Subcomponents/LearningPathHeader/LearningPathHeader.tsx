import React, { useState, useEffect, useRef } from 'react';
import { useDispatch } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';
import { addFilterParams } from '../../../../../../redux/Slices/AllLearningPathFilterParamsSlice';
import {
  ALL_LEARNING_PATHS, SAVED_LEARNING_PATHS, SORT_BY, DIFFICULTY, TITLE, RATING,
} from '../../../../../../constants/Strings';
import styles from './LearningPathHeader.module.scss';

enum LearningPathNav {
  AllLearningPath='AllLearningPath',
  SavedLearningPath='SavedLearningPath'
}

const LearningPathHeader = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const params = new URLSearchParams(location.search);
  const selectionRef = useRef<HTMLSelectElement>(null);
  const [sortingFeature, setSortingFeature] = useState(RATING);
  const [activeNavType, setActiveNavType] = useState(LearningPathNav.AllLearningPath);
  const {
    learningPathsHeader, sortingContainer, activeNav, nonActiveNav,
  } = styles;

  const switchSortingFeatures = (e:React.SyntheticEvent) => {
    setSortingFeature((e.target as HTMLSelectElement).value);
  };
  useEffect(() => {
    if (params.has('sort'))setSortingFeature(params.get('sort') as string);
  }, []);
  useEffect(() => {
    params.set('sort', sortingFeature);
    dispatch(addFilterParams({ sort: sortingFeature }));
    navigate(`/exploreLearningPaths?${params}`);
  }, [sortingFeature]);

  return (
    <div className={learningPathsHeader}>
      <nav>
        <p className={activeNavType === 'AllLearningPath' ? activeNav : nonActiveNav}>{ALL_LEARNING_PATHS}</p>
        <p className={activeNavType === 'SavedLearningPath' ? activeNav : nonActiveNav}>{SAVED_LEARNING_PATHS}</p>
      </nav>
      <div className={sortingContainer}>
        <label htmlFor="sorting">{SORT_BY}</label>
        <select ref={selectionRef} value={sortingFeature} name="sorting" id="sorting" onChange={switchSortingFeatures}>
          <option value={RATING}>{RATING}</option>
          <option value={DIFFICULTY}>{DIFFICULTY}</option>
          <option value={TITLE}>{TITLE}</option>
        </select>
      </div>
    </div>
  );
};
export default LearningPathHeader;
