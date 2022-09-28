import { useEffect, useContext } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import { SuggestedCourseType } from '../../../../types/CourseTypes';
import LearningPath from '../../../../component/LearningPath/LearningPath';
import { getUserSavedCourse } from '../../../../redux/Slices/SavedLearningPathSlice';
import ContentRenderer from '../../../../component/ContentRenderer/ContentRenderer';
import { filterSendingParams } from '../../helpers';
import { userContext } from '../../../../contexts/Contexts';
import styles from './SavedCoursesContent.module.scss';

/* eslint-disable max-len */
const SavedCoursesContent = () => {
  const { token, id: userId } = useContext(userContext);
  const dispatch = useDispatch();
  const savedCoursesState = useSelector<RootState>((state) => state.savedCourse);
  const sendingParams = useSelector<RootState>((state) => state.filterParams);
  const deletedSavedCourseState = useSelector<RootState>((state) => state.deleteUserSavedCourse) as {entity:SuggestedCourseType, isLoading:boolean, errorMessage:string};
  const filteredParams = filterSendingParams(sendingParams as object);
  const { entity, isLoading, errorMessage } = savedCoursesState as {entity:SuggestedCourseType[], isLoading:boolean, errorMessage:string};
  const { mainContainer } = styles;

  useEffect(() => {
    dispatch(getUserSavedCourse({ userId, token, params: filteredParams }));
  }, [sendingParams, deletedSavedCourseState]);

  return (
    <div className={mainContainer}>
      <ContentRenderer
        isLoading={isLoading}
        errorMessage={errorMessage}
        entity={entity}
        errorFieldClassName="allLearningPathErrorStyle"
        render={(savedCourses) => (
          savedCourses.map((course:SuggestedCourseType) => (
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
