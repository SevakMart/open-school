import React, { useState, useEffect, useRef } from 'react';
import { useDispatch } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';
import { addFilterParams } from '../../../../../../redux/Slices/AllLearningPathFilterParamsSlice';
import {
  ALL_LEARNING_PATHS, SAVED_LEARNING_PATHS, SORT_BY, DIFFICULTY, TITLE, RATING,
} from '../../../../../../constants/Strings';
import { CourseContent } from '../../../../../../types/CourseContent';
import styles from './LearningPathHeader.module.scss';

enum LearningPathNav {
  AllLearningPath='AllLearningPath',
  SavedLearningPath='SavedLearningPath'
}

const LearningPathHeader = ({ activeNavigator }:
  {activeNavigator:CourseContent}) => {
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const params = new URLSearchParams(location.search);
  const selectionRef = useRef<HTMLSelectElement>(null);
  const [sortingFeature, setSortingFeature] = useState(RATING);

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
    if (activeNavigator === CourseContent.ALLCOURSES) {
      params.set('sort', sortingFeature);
      dispatch(addFilterParams({ sort: sortingFeature }));
      navigate(`/exploreLearningPaths?${params}`);
    }
  }, [sortingFeature]);

  const goToAllCourses = () => {
    navigate(-1);
  };
  const goToUserSavedCourses = () => {
    navigate('/exploreLearningPaths/savedCourses');
  };

  return (
    <div className={learningPathsHeader}>
      <nav>
        <p
          className={activeNavigator === CourseContent.ALLCOURSES ? activeNav : nonActiveNav}
          data-testid={LearningPathNav.AllLearningPath}
          onClick={goToAllCourses}
        >
          {ALL_LEARNING_PATHS}

        </p>
        <p
          className={activeNavigator === CourseContent.SAVEDCOURSES ? activeNav : nonActiveNav}
          data-testid={LearningPathNav.SavedLearningPath}
          onClick={goToUserSavedCourses}
        >
          {SAVED_LEARNING_PATHS}

        </p>
      </nav>
      {activeNavigator === CourseContent.ALLCOURSES
      && (
      <div className={sortingContainer}>
        <label data-testid="sorting" htmlFor="sorting">{SORT_BY}</label>
        <select ref={selectionRef} value={sortingFeature} name="sorting" id="sorting" onChange={switchSortingFeatures}>
          <option value={RATING}>{RATING}</option>
          <option value={DIFFICULTY}>{DIFFICULTY}</option>
          <option value={TITLE}>{TITLE}</option>
        </select>
      </div>
      )}
    </div>
  );
};
export default LearningPathHeader;
