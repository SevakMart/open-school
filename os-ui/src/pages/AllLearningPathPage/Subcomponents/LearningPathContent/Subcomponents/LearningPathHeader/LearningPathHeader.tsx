import React, {
  useState, useEffect, useRef, useContext,
} from 'react';
import { useDispatch } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { addFilterParams } from '../../../../../../redux/Slices/AllLearningPathFilterParamsSlice';
import {
  ALL_LEARNING_PATHS, SAVED_LEARNING_PATHS, DIFFICULTY, TITLE, RATING,
} from '../../../../../../constants/Strings';
import { headerTitleContext } from '../../../../../../contexts/Contexts';
import styles from './LearningPathHeader.module.scss';

const LearningPathHeader = ({ handleChangeHeader }:
  {handleChangeHeader:(headerTitle:string)=>void}) => {
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { t } = useTranslation();
  const focusedHeader = useContext(headerTitleContext);
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
    if (focusedHeader === ALL_LEARNING_PATHS) {
      params.set('sort', sortingFeature);
      dispatch(addFilterParams({ sort: sortingFeature }));
      navigate(`/exploreLearningPaths?${params}`);
    }
  }, [sortingFeature]);

  return (
    <div className={learningPathsHeader} style={focusedHeader === ALL_LEARNING_PATHS ? { paddingLeft: '20%' } : { paddingLeft: '6%' }}>
      <nav>
        <p
          className={focusedHeader === ALL_LEARNING_PATHS ? activeNav : nonActiveNav}
          data-testid={ALL_LEARNING_PATHS}
          onClick={() => handleChangeHeader(SAVED_LEARNING_PATHS)}
        >
          {t('All Learning Paths')}

        </p>
        <p
          className={focusedHeader === SAVED_LEARNING_PATHS ? activeNav : nonActiveNav}
          data-testid={SAVED_LEARNING_PATHS}
          onClick={() => handleChangeHeader(ALL_LEARNING_PATHS)}
        >
          {t('Saved Learning Paths')}

        </p>
      </nav>
      {focusedHeader === ALL_LEARNING_PATHS
      && (
      <div className={sortingContainer}>
        <label data-testid="sorting" htmlFor="sorting">{t('Sort By')}</label>
        <select ref={selectionRef} value={sortingFeature} name="sorting" id="sorting" onChange={switchSortingFeatures}>
          <option value={RATING}>{t('Rating')}</option>
          <option value={DIFFICULTY}>{t('difficulty_id')}</option>
          <option value={TITLE}>{(t('Title'))}</option>
        </select>
      </div>
      )}
    </div>
  );
};
export default LearningPathHeader;
