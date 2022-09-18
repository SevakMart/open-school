import React, { useEffect, useContext } from 'react';
import { useTranslation } from 'react-i18next';
import { useLocation } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import { SuggestedCourseType } from '../../../../types/SuggestedCourseType';
import LearningPath from '../../../../component/LearningPath/LearningPath';
import { getUserSavedCourse } from '../../../../redux/Slices/SavedLearningPathSlice';
import Loader from '../../../../component/Loader/Loader';
import { ErrorField } from '../../../../component/ErrorField/ErrorField';
import { userContext } from '../../../../contexts/Contexts';
import styles from './SavedCoursesContent.module.scss';

/* eslint-disable max-len */
const SavedCoursesContent = () => {
  const { token, id: userId } = useContext(userContext);
  const dispatch = useDispatch();
  const savedCourses = useSelector<RootState>((state) => state.savedCourse);
  const { entity, isLoading, errorMessage } = savedCourses as {entity:SuggestedCourseType[], isLoading:boolean, errorMessage:string};
  const { t } = useTranslation();
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const { mainContainer } = styles;
  const NoDisplayedDataMessage = <h2 data-testid="Error Message">{t('messages.noData.default')}</h2>;

  useEffect(() => {
    dispatch(getUserSavedCourse({ userId, token }));
  }, [params]);

  return (
    <div className={mainContainer}>
      {isLoading && <Loader />}
      {errorMessage !== '' && (
        <ErrorField.MainErrorField className={['allLearningPathErrorStyle']}>
          {errorMessage}
        </ErrorField.MainErrorField>
      )}
      {entity.length === 0 && !isLoading && errorMessage.length === 0 && NoDisplayedDataMessage }
      {entity.length > 0 && entity.map((course) => (
        <React.Fragment key={course.title}>
          <LearningPath
            courseInfo={course}
          />
        </React.Fragment>
      ))}
    </div>

  );
};
export default SavedCoursesContent;
