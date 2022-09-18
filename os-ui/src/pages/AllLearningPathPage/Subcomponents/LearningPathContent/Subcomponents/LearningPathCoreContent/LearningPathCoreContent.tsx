import { useEffect, useContext } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { RootState } from '../../../../../../redux/Store';
import { userContext } from '../../../../../../contexts/Contexts';
import LearningPath from '../../../../../../component/LearningPath/LearningPath';
import Loader from '../../../../../../component/Loader/Loader';
import { ErrorField } from '../../../../../../component/ErrorField/ErrorField';
import { SuggestedCourseType } from '../../../../../../types/SuggestedCourseType';
import { filterSendingParams } from '../../../../helpers';
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
  const { t } = useTranslation();
  const NoDisplayedDataMessage = <h2 data-testid="Error Message">{t('messages.noData.default')}</h2>;
  const { mainCoreContainer, courseContainer } = styles;

  useEffect(() => {
    dispatch(getAllLearningPathCourses({ token, params: filteredParams }));
  }, [sendingParams]);

  return (
    <div className={mainCoreContainer}>
      {isLoading && <Loader />}
      {errorMessage !== '' && (
        <ErrorField.MainErrorField className={['allLearningPathErrorStyle']}>
          {errorMessage}
        </ErrorField.MainErrorField>
      )}
      {entity.length === 0 && !isLoading && errorMessage.length === 0 && NoDisplayedDataMessage }
      {entity.length > 0 && entity.map((course) => (
        <div className={courseContainer} key={course.title}>
          <LearningPath
            courseInfo={course}
          />
        </div>
      )) }
    </div>
  );
};
export default LearningPathCoreContent;
