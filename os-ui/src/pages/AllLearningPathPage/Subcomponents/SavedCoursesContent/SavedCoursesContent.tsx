import { useEffect, useContext } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import { SuggestedCourseType } from '../../../../types/CourseTypes';
import LearningPath from '../../../../component/LearningPath/LearningPath';
import { getUserSavedCourse } from '../../../../redux/Slices/SavedLearningPathSlice';
import ContentRenderer from '../../../../component/ContentRenderer/ContentRenderer';
import { filterSendingParams } from '../../helpers';
import { userContext } from '../../../../contexts/Contexts';
import { deleteUserSavedCourse } from '../../../../redux/Slices/DeleteUserSavedCourse';
import styles from './SavedCoursesContent.module.scss';

/* eslint-disable max-len */
const SavedCoursesContent = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const params = new URLSearchParams(location.search);
  const { token, id: userId } = useContext(userContext);
  const dispatch = useDispatch();
  const savedCoursesState = useSelector<RootState>((state) => state.savedCourse);
  const sendingParams = useSelector<RootState>((state) => state.filterParams);
  const deletedSavedCourseState = useSelector<RootState>((state) => state.deleteUserSavedCourse) as {entity:SuggestedCourseType, isLoading:boolean, errorMessage:string};
  const filteredParams = filterSendingParams(sendingParams as object);
  const { entity, isLoading, errorMessage } = savedCoursesState as {entity:SuggestedCourseType[], isLoading:boolean, errorMessage:string};
  const { mainContainer } = styles;

  const deleteSavedCourse = (courseTitle:string, courseId:number) => {
    dispatch(deleteUserSavedCourse({ userId, courseId, token }));
    params.delete(courseTitle);
    navigate(`${location.pathname}?${params}`);
  };

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
              deleteCourse={deleteSavedCourse}
            />
          ))
        )}
      />
    </div>
  );
};
export default SavedCoursesContent;
