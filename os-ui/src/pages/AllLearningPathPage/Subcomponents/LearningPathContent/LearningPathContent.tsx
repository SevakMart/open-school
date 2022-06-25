import {
  useState, useEffect, useRef,
} from 'react';
import { useDispatch } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';
import Search from '../../../../component/Search/Search';
import LearningPathCoreContent from './Subcomponents/LearningPathCoreContent/LearningPathCoreContent';
import { addFilterParams, removeFilterParams } from '../../../../redux/Slices/AllLearningPathFilterParamsSlice';
import styles from './LearningPathContent.module.scss';
/* eslint-disable-next-line max-len */
const LearningPathContent = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();
  const [searchTitle, setSearchTitle] = useState('');
  const mainContentRef = useRef<HTMLDivElement|null>(null);
  const params = new URLSearchParams(location.search);
  const { learningPathsMainContainer } = styles;

  useEffect(() => {
    if (params.get('courseTitle')) setSearchTitle(params.get('courseTitle') as string);
  }, []);

  useEffect(() => {
    if (searchTitle) {
      dispatch(addFilterParams({ courseTitle: searchTitle }));
      params.set('courseTitle', searchTitle);
      navigate(`/exploreLearningPaths?${params}`);
    } else {
      params.delete('courseTitle');
      dispatch(removeFilterParams({ courseTitle: searchTitle }));
      navigate(`/exploreLearningPaths?${params}`);
    }
  }, [searchTitle]);

  return (
    <div ref={mainContentRef} className={learningPathsMainContainer}>
      <Search changeUrlQueries={(title:string) => setSearchTitle(title)} />
      <LearningPathCoreContent />
    </div>
  );
};
export default LearningPathContent;
