import { useEffect, useContext } from 'react';
import { useLocation } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import { SuggestedCourseType } from '../../../../types/SuggestedCourseType';
import LearningPath from '../../../../component/LearningPath/LearningPath';
import { getUserSavedCourse } from '../../../../redux/Slices/SavedLearningPathSlice';
import ContentRenderer from '../../../../component/ContentRenderer/ContentRenderer';
import { userContext } from '../../../../contexts/Contexts';
import styles from './SavedCoursesContent.module.scss';

/* eslint-disable max-len */
const SavedCoursesContent = () => {
  const { token, id: userId } = useContext(userContext);
  const dispatch = useDispatch();
  const savedCourses = useSelector<RootState>((state) => state.savedCourse);
  const { entity, isLoading, errorMessage } = savedCourses as {entity:SuggestedCourseType[], isLoading:boolean, errorMessage:string};
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const { mainContainer } = styles;

  useEffect(() => {
    dispatch(getUserSavedCourse({ userId, token }));
  }, [params]);

  return (
    <div className={mainContainer}>
      <ContentRenderer
        isLoading={isLoading}
        errorMessage={errorMessage}
        entity={entity}
        errorFieldClassName="allLearningPathErrorStyle"
        render={(entity) => (
          entity.map((course) => (
            <LearningPath
              key={course.title}
              courseInfo={course}
            />
          ))
        )}
      />
    </div>

  );
};
export default SavedCoursesContent;
