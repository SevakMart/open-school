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
enum CourseContent {
  ALLCOURSES='All Courses',
  SAVEDCOURSES='Saved Courses',
}

const LearningPathHeader = ({ setContentType }:
  {setContentType:(contentType:CourseContent)=>void}) => {
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const params = new URLSearchParams(location.search);
  const selectionRef = useRef<HTMLSelectElement>(null);
  const [sortingFeature, setSortingFeature] = useState(RATING);
  const [activeNavType, setActiveNavType] = useState<string|undefined>(
    LearningPathNav.AllLearningPath,
  );
  const [isSavedCourseContent, setIsSavedCourseContent] = useState(false);
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
    if (isSavedCourseContent) setContentType(CourseContent.SAVEDCOURSES);
    else setContentType(CourseContent.ALLCOURSES);
    params.set('sort', sortingFeature);
    dispatch(addFilterParams({ sort: sortingFeature }));
    navigate(`/exploreLearningPaths?${params}`);
  }, [sortingFeature, isSavedCourseContent]);

  const changeNavigation = (e:React.SyntheticEvent) => {
    setActiveNavType((e.target as HTMLParagraphElement).dataset.testid);
    setIsSavedCourseContent((prevState) => !prevState);
  };

  return (
    <div className={learningPathsHeader}>
      <nav>
        <p className={activeNavType === 'AllLearningPath' ? activeNav : nonActiveNav} data-testid={LearningPathNav.AllLearningPath} onClick={changeNavigation}>{ALL_LEARNING_PATHS}</p>
        <p className={activeNavType === 'SavedLearningPath' ? activeNav : nonActiveNav} data-testid={LearningPathNav.SavedLearningPath} onClick={changeNavigation}>{SAVED_LEARNING_PATHS}</p>
      </nav>
      {!isSavedCourseContent
      && (
      <div className={sortingContainer}>
        <label htmlFor="sorting">{SORT_BY}</label>
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
