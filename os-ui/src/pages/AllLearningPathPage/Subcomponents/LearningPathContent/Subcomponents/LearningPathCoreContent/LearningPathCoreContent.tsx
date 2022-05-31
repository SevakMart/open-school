import { useState, useEffect, useContext } from 'react';
import { useLocation } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from '../../../../../../redux/Store';
import { tokenContext } from '../../../../../../contexts/Contexts';
import LearningPath from '../../../../../../component/LearningPath/LearningPath';
import courseService from '../../../../../../services/courseService';
import { SuggestedCourseType } from '../../../../../../types/SuggestedCourseType';
import { EMPTY_DATA_ERROR_MESSAGE } from '../../../../../../constants/Strings';
import styles from './LearningPathCoreContent.module.scss';

const LearningPathCoreContent = () => {
  const location = useLocation();
  const sendingParams = useSelector<RootState>((state) => state.filterParams);
  const token = useContext(tokenContext);
  const [courseList, setCourseList] = useState<SuggestedCourseType[]>([]);
  const { mainCoreContainer, courseContainer } = styles;

  useEffect(() => {
    courseService.getSearchedCourses({ page: 0, size: 100, ...(sendingParams as object) }, token)
      .then((data) => { console.log(data); setCourseList([...data.content]); });
  }, [sendingParams]);
  console.log(sendingParams);
  return (
    <div className={mainCoreContainer}>
      {courseList.length ? courseList.map((course, index) => (
        <div className={courseContainer} key={index}>
          <LearningPath
            title={course.title}
            rating={course.rating}
            difficulty={course.difficulty}
            keywords={course.keywords}
          />
        </div>
      )) : <h2>{EMPTY_DATA_ERROR_MESSAGE}</h2>}
    </div>
  );
};
export default LearningPathCoreContent;
