import { useEffect, useContext } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { userContext } from '../../../../../../contexts/Contexts';
import { SuggestedCourseType } from '../../../../../../types/CourseTypes';
import { getAllLearningPathCourses } from '../../../../../../redux/Slices/AllLearningPathCourseSlice';
import { getUserSavedCourse } from '../../../../../../redux/Slices/SavedLearningPathSlice';
import { deleteUserSavedCourse } from '../../../../../../redux/Slices/DeleteUserSavedCourseSlice';
import { saveUserPreferredCourse } from '../../../../../../redux/Slices/SaveUserPreferredCourseSlice';
import { RootState } from '../../../../../../redux/Store';
import styles from './LearningPathCoreContent.module.scss';
import LearningPath from '../../../../../../component/LearningPath/LearningPath';
import ContentRenderer from '../../../../../../component/ContentRenderer/ContentRenderer';
import { filterSendingParams } from '../../../../helpers';

const LearningPathCoreContent = () => {
  const { token, id: userId } = useContext(userContext);
  const dispatch = useDispatch();
  const sendingParams = useSelector<RootState>((state) => state.filterParams);
  const allLearningPathCourseState = useSelector<RootState>((state) => state.allLearningPathCourses);
  const { entity, isLoading, errorMessage } = allLearningPathCourseState as {entity:SuggestedCourseType[], isLoading:boolean, errorMessage:string};
  const filteredParams = filterSendingParams(sendingParams as object);
  const { mainCoreContainer, courseContainer } = styles;

  const saveCourse = (courseTitle:string, courseId:number) => {
    dispatch(saveUserPreferredCourse({ userId, courseId, token }));
  };

  const deleteCourse = (courseTitle:string, courseId:number) => {
    dispatch(deleteUserSavedCourse({ userId, courseId, token }));
  };

  useEffect(() => {
    dispatch(getUserSavedCourse({
	  userId, token, params: {},
    }));
  }, []);

  useEffect(() => {
    dispatch(getAllLearningPathCourses({ token, params: filteredParams }));
  }, [sendingParams]);

  return (
    <div className={mainCoreContainer}>
      <ContentRenderer
        isLoading={isLoading}
        errorMessage={errorMessage}
        entity={entity}
        errorFieldClassName="allLearningPathErrorStyle"
        render={(entity) => (
          entity.map((course:SuggestedCourseType) => (
            <div className={courseContainer} key={course.id}>
              <LearningPath
                courseInfo={course}
                saveCourse={saveCourse}
                deleteCourse={deleteCourse}
              />
            </div>
          ))
        )}
      />
    </div>
  );
};

export default LearningPathCoreContent;
