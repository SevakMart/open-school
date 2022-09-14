import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useLocation, useNavigate } from 'react-router-dom';
import { useCheck } from '../../../../custom-hooks/useCheck';
import { RATING, DIFFICULTY, TITLE } from '../../constant';
import { Input } from '../../../../component/Input/Input';
import { addFilterParams, removeFilterParams } from '../../../../redux/Slices/AllLearningPathFilterParamsSlice';
import styles from './CourseContentHeader.module.scss';

enum HeaderPath {
    ALL_LEARNING_PATHS='All learning Path',
    SAVED_LEARNING_PATHS='Saved Learning Path'
}

const CourseContentHeader = () => {
  const [focusedHeader, setFocusedHeader] = useState(HeaderPath.ALL_LEARNING_PATHS);
  const [sortingFeature, setSortingFeature] = useState(RATING);
  const location = useLocation();
  const navigate = useNavigate();
  const params = new URLSearchParams(location.search);
  const { t } = useTranslation();
  const [, , dispatch, handleSearchedResult] = useCheck('courseTitle', '');
  const {
    activeNav, nonActiveNav, headerMainContainer, sortingContainer, navAndSortContent,
  } = styles;

  const handleSearch = (title:string) => {
    if (title) {
      dispatch(addFilterParams({ courseTitle: title }));
    } else dispatch(removeFilterParams({ courseTitle: title }));
    handleSearchedResult(title);
  };
  const switchSortingFeatures = (e:React.SyntheticEvent) => {
    setSortingFeature((e.target as HTMLSelectElement).value);
  };

  useEffect(() => {
    if (params.has('sort'))setSortingFeature(params.get('sort') as string);
  }, []);

  useEffect(() => {
    if (focusedHeader === HeaderPath.ALL_LEARNING_PATHS) {
      params.set('sort', sortingFeature);
      dispatch(addFilterParams({ sort: sortingFeature }));
      navigate(`/exploreLearningPaths?${params}`);
    }
  }, [sortingFeature]);

  return (
    <div className={headerMainContainer}>
      <div className={navAndSortContent}>
        <nav>
          <p
            className={focusedHeader === HeaderPath.ALL_LEARNING_PATHS ? activeNav : nonActiveNav}
            data-testid={HeaderPath.ALL_LEARNING_PATHS}
            // onClick={() => handleChangeHeader(SAVED_LEARNING_PATHS)}
          >
            {t('string.learningPath.all')}

          </p>
          <p
            className={focusedHeader === HeaderPath.SAVED_LEARNING_PATHS ? activeNav : nonActiveNav}
            data-testid={HeaderPath.SAVED_LEARNING_PATHS}
            // onClick={() => handleChangeHeader(ALL_LEARNING_PATHS)}
          >
            {t('string.learningPath.saved')}

          </p>
        </nav>
        {focusedHeader === HeaderPath.ALL_LEARNING_PATHS
      && (
      <div className={sortingContainer}>
        <label data-testid="sorting" htmlFor="sorting">{t('string.learningPath.sortBy')}</label>
        <select value={sortingFeature} name="sorting" id="sorting" onChange={switchSortingFeatures}>
          <option value={RATING}>{t('string.learningPath.rating')}</option>
          <option value={DIFFICULTY}>{t('string.learningPath.difficulty')}</option>
          <option value={TITLE}>{(t('string.learningPath.title'))}</option>
        </select>
      </div>
      ) }
      </div>
      <Input.SearchInput
        className={['AllLearningPathSearch']}
        changeUrlQueries={handleSearch}
      />
    </div>
  );
};

export default CourseContentHeader;
