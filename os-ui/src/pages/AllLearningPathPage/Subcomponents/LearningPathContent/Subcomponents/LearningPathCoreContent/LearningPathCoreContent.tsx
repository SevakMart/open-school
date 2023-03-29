import { useEffect, useContext } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { DispatchType, RootState } from '../../../../../../redux/Store';
import { userContext } from '../../../../../../contexts/Contexts';
import LearningPath from '../../../../../../component/LearningPath/LearningPath';
import userService from '../../../../../../services/userService';
import { SuggestedCourseType } from '../../../../../../types/CourseTypes';
import { filterSendingParams } from '../../../../helpers';
import ContentRenderer from '../../../../../../component/ContentRenderer/ContentRenderer';
import { getAllLearningPathCourses } from '../../../../../../redux/Slices/AllLearningPathCourseSlice';
import { getUserSavedCourse } from '../../../../../../redux/Slices/SavedLearningPathSlice';
import { deleteUserSavedCourse } from '../../../../../../redux/Slices/DeleteUserSavedCourse';
import styles from './LearningPathCoreContent.module.scss';

/* eslint-disable max-len */

const LearningPathCoreContent = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const params = new URLSearchParams(location.search);
  const sendingParams = useSelector<RootState>((state) => state.filterParams);
  const allLearningPathCourseState = useSelector<RootState>((state) => state.allLearningPathCourses);
  const { entity, isLoading, errorMessage } = allLearningPathCourseState as {entity:SuggestedCourseType[], isLoading:boolean, errorMessage:string};
  const filteredParams = filterSendingParams(sendingParams as object);
  const { token, id: userId } = useContext(userContext);
  const dispatch = useDispatch<DispatchType>();
  const { mainCoreContainer, courseContainer } = styles;

  const saveCourse = (courseTitle:string, courseId:number) => {
    userService.saveUserPreferredCourses(userId, courseId, token);
    params.set(courseTitle, String(courseId));
    navigate(`${location.pathname}?${params}`);
  };
  const deleteCourse = (courseTitle:string, courseId:number) => {
    dispatch(deleteUserSavedCourse({ userId, courseId, token }));
    params.delete(courseTitle);
    navigate(`${location.pathname}?${params}`);
  };

  useEffect(() => {
    if (!params.toString()) {
      dispatch(getUserSavedCourse({ userId, token, params: filteredParams }))
        .unwrap()
        .then((savedCourseList:SuggestedCourseType[]) => {
          for (const savedCourse of savedCourseList) {
            params.set(savedCourse.title, String(savedCourse.id));
          }
          navigate(`${location.pathname}?${params}`, { replace: true });
        });
    }
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
