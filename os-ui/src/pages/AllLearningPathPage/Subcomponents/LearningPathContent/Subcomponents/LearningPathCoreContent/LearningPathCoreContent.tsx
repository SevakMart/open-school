import { useState, useEffect, useContext } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../../../../../redux/Store';
import { userContext, courseBookmarkContext } from '../../../../../../contexts/Contexts';
import LearningPath from '../../../../../../component/LearningPath/LearningPath';
import courseService from '../../../../../../services/courseService';
import { SuggestedCourseType } from '../../../../../../types/SuggestedCourseType';
import { EMPTY_DATA_ERROR_MESSAGE } from '../../../../../../constants/Strings';
import styles from './LearningPathCoreContent.module.scss';

type CourseListType=SuggestedCourseType & {id:number}

const LearningPathCoreContent = () => {
  const sendingParams = useSelector<RootState>((state) => state.filterParams);
  const { token } = useContext(userContext);
  const [courseList, setCourseList] = useState<CourseListType[]>([]);
  const { mainCoreContainer, courseContainer } = styles;

  useEffect(() => {
    courseService.getSearchedCourses({
      page: 0,
      size: 100,
      ...(sendingParams as object),
    }, token)
      .then((data) => setCourseList([...data.content]));
  }, [sendingParams]);

  return (
    <div className={mainCoreContainer}>
      {courseList.length ? courseList.map((course, index) => (
        <div className={courseContainer} key={index}>
          <courseBookmarkContext.Provider value={course.id}>
            <LearningPath
              title={course.title}
              rating={course.rating}
              difficulty={course.difficulty}
              keywords={course.keywords}
            />
          </courseBookmarkContext.Provider>
        </div>
      )) : <h2>{EMPTY_DATA_ERROR_MESSAGE}</h2>}
    </div>
  );
};
export default LearningPathCoreContent;
