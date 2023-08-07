import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useCheck } from '../../../../custom-hooks/useCheck';
import { RATING, DIFFICULTY, TITLE } from '../../constant';
import { Input } from '../../../../component/Input/Input';
import { addFilterParams, removeFilterParams } from '../../../../redux/Slices/AllLearningPathFilterParamsSlice';
import styles from './CourseContentHeader.module.scss';

export enum HeaderPath {
    ALL_LEARNING_PATHS='All learning Path',
    SAVED_LEARNING_PATHS='Saved Learning Path'
}
/* eslint-disable max-len */
const CourseContentHeader = ({ handleChangeHeader }:{handleChangeHeader:(headerTitle:HeaderPath)=>void}) => {
  const [focusedHeader, setFocusedHeader] = useState(HeaderPath.ALL_LEARNING_PATHS);
  const { t } = useTranslation();
  const [, , dispatch, handleSearchedResult] = useCheck('courseTitle', '');
  const [sortingFeature, setSortingFeature] = useState<any>(RATING);
  const [key, setKey] = useState(0);
  const {
    activeNav, nonActiveNav, headerMainContainer, sortingContainer, navAndSortContent,
  } = styles;

  const handleSearch = (title:string) => {
    if (title) {
      dispatch(addFilterParams({ courseTitle: title }));
    } else dispatch(removeFilterParams({ courseTitle: title }));
    handleSearchedResult(title);
  };

  useEffect(() => {
    handleSearch('');
  }, [focusedHeader]);

  useEffect(() => {
    if (focusedHeader === HeaderPath.ALL_LEARNING_PATHS) {
      dispatch(addFilterParams({ sort: sortingFeature }));
    }
  }, [sortingFeature]);

  const handleSavedLearningPathClick = () => {
    dispatch(removeFilterParams(''));
    handleChangeHeader(HeaderPath.SAVED_LEARNING_PATHS);
    setFocusedHeader(HeaderPath.SAVED_LEARNING_PATHS);
    setKey((prevKey) => prevKey + 1);
  };

  const handleAllLearningPathClick = () => {
    dispatch(removeFilterParams(''));
    handleChangeHeader(HeaderPath.ALL_LEARNING_PATHS);
    setFocusedHeader(HeaderPath.ALL_LEARNING_PATHS);
    setKey((prevKey) => prevKey + 1);
  };

  return (
    <div key={key} className={headerMainContainer}>
      <div className={navAndSortContent}>
        <nav>
          <p
            className={focusedHeader === HeaderPath.ALL_LEARNING_PATHS ? activeNav : nonActiveNav}
            data-testid={HeaderPath.ALL_LEARNING_PATHS}
            onClick={handleAllLearningPathClick}
          >
            {t('string.learningPath.all')}

          </p>
          <p
            className={focusedHeader === HeaderPath.SAVED_LEARNING_PATHS ? activeNav : nonActiveNav}
            data-testid={HeaderPath.SAVED_LEARNING_PATHS}
            onClick={handleSavedLearningPathClick}
          >
            {t('string.learningPath.saved')}

          </p>
        </nav>
        {focusedHeader === HeaderPath.ALL_LEARNING_PATHS
      && (
      <div className={sortingContainer}>
        <label data-testid="sorting" htmlFor="sorting">{t('string.learningPath.sortBy')}</label>
        <select value={sortingFeature} name="sorting" id="sorting" onChange={(e) => setSortingFeature(e.target.value)}>
          <option value={RATING}>{t('string.learningPath.rating')}</option>
          <option value={DIFFICULTY}>{t('string.learningPath.difficulty')}</option>
          <option value={TITLE}>{(t('string.learningPath.title'))}</option>
        </select>
      </div>
      ) }
      </div>
      <Input.SearchInput
        className={['search', 'search__allLearningPathSearch']}
        changeUrlQueries={handleSearch}
        placeholder={t('Search by name')}
      />
    </div>
  );
};

export default CourseContentHeader;
