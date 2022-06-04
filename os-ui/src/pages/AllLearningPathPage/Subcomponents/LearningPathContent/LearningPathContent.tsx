import {
  useState, useEffect, useContext, useRef,
} from 'react';
import { useDispatch } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';
import LearningPathHeader from './Subcomponents/LearningPathHeader/LearningPathHeader';
import Search from '../../../../component/Search/Search';
import LearningPathCoreContent from './Subcomponents/LearningPathCoreContent/LearningPathCoreContent';
import { addFilterParams, removeFilterParams } from '../../../../redux/Slices/AllLearningPathFilterParamsSlice';
import { CourseContent } from '../../../../types/CourseContent';
import { courseContentContext } from '../../../../contexts/Contexts';
import styles from './LearningPathContent.module.scss';

const LearningPathContent = ({ filterTabIsVisible, changeContentType }:
  {filterTabIsVisible:boolean, changeContentType?:(contentType:CourseContent)=>void}) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();
  const [searchTitle, setSearchTitle] = useState('');
  const mainContentRef = useRef<HTMLDivElement|null>(null);
  // const contentType = useContext(courseContentContext);
  const params = new URLSearchParams(location.search);

  const { learningPathsMainContainer } = styles;

  // const handleContentType = (contentType:CourseContent) => changeContentType(contentType);

  useEffect(() => {
    if (params.get('courseTitle')) setSearchTitle(params.get('courseTitle') as string);
  }, []);

  useEffect(() => {
    /* if (filterTabIsVisible) {
      (mainContentRef.current as HTMLDivElement).setAttribute('style', 'width:75%;transition-duration: 0.5s');
    } else (mainContentRef.current as HTMLDivElement).setAttribute('style', 'width:98%;transition-duration: 0.5s'); */
    /* if (contentType === CourseContent.SAVEDCOURSES) {
      (mainContentRef.current as HTMLDivElement).setAttribute('style', 'padding-left:6%;width:100%');
    } */
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
    <div ref={mainContentRef} className={learningPathsMainContainer} style={{ transitionDuration: '0.5s', width: filterTabIsVisible ? '75%' : '98%' }}>
      <LearningPathHeader
        // setContentType={handleContentType}
        activeNavigator={CourseContent.ALLCOURSES}
      />
      <Search changeUrlQueries={(title:string) => setSearchTitle(title)} paddingLeft="0" leftPosition={filterTabIsVisible ? '20%' : '15%'} />
      <LearningPathCoreContent />
    </div>
  );
};
export default LearningPathContent;
