import { useState, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';
import LearningPathHeader from './Subcomponents/LearningPathHeader/LearningPathHeader';
import Search from '../../../../component/Search/Search';
import LearningPathCoreContent from './Subcomponents/LearningPathCoreContent/LearningPathCoreContent';
import SavedCoursesContent from '../SavedCoursesContent/SavedCoursesContent';
import { addFilterParams, removeFilterParams } from '../../../../redux/Slices/AllLearningPathFilterParamsSlice';
import styles from './LearningPathContent.module.scss';

enum CourseContent {
  ALLCOURSES='All Courses',
  SAVEDCOURSES='Saved Courses',
}

const LearningPathContent = ({ filterTabIsVisible }:{filterTabIsVisible:boolean}) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();
  const [searchTitle, setSearchTitle] = useState('');
  const [contentType, setContentType] = useState(CourseContent.ALLCOURSES);
  const params = new URLSearchParams(location.search);
  const { learningPathsMainContainer } = styles;

  const handleContentType = (contentType:CourseContent) => {
    setContentType(contentType);
  };

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
    <div className={learningPathsMainContainer} style={filterTabIsVisible ? { width: '75%', transitionDuration: '2s' } : { width: '98%', transitionDuration: '2s' }}>
      <LearningPathHeader setContentType={handleContentType} />
      {contentType === CourseContent.ALLCOURSES
        ? (
          <>
            <Search changeUrlQueries={(title:string) => setSearchTitle(title)} paddingLeft="0" leftPosition={filterTabIsVisible ? '20%' : '10%'} />
            <LearningPathCoreContent />
          </>
        )
        : <SavedCoursesContent />}
    </div>
  );
};
export default LearningPathContent;
