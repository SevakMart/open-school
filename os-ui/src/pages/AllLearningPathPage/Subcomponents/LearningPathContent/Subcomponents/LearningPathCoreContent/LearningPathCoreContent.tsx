import { useEffect, useContext } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../../../../redux/Store';
import { userContext } from '../../../../../../contexts/Contexts';
import LearningPath from '../../../../../../component/LearningPath/LearningPath';
import { SuggestedCourseType } from '../../../../../../types/SuggestedCourseType';
import { filterSendingParams } from '../../../../helpers';
import ContentRenderer from '../../../../../../component/ContentRenderer/ContentRenderer';
import { getAllLearningPathCourses } from '../../../../../../redux/Slices/AllLearningPathCourseSlice';
import styles from './LearningPathCoreContent.module.scss';

/* eslint-disable max-len */

const LearningPathCoreContent = () => {
  const sendingParams = useSelector<RootState>((state) => state.filterParams);
  const allLearningPathCourseState = useSelector<RootState>((state) => state.allLearningPathCourses);
  const { entity, isLoading, errorMessage } = allLearningPathCourseState as {entity:SuggestedCourseType[], isLoading:boolean, errorMessage:string};
  const filteredParams = filterSendingParams(sendingParams as object);
  const { token } = useContext(userContext);
  const dispatch = useDispatch();
  const { mainCoreContainer, courseContainer } = styles;

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
          entity.map((course) => (
            <div className={courseContainer} key={course.title}>
              <LearningPath
                courseInfo={course}
              />
            </div>
          ))
        )}
      />
    </div>
  );
};
export default LearningPathCoreContent;
