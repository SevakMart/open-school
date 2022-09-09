import { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import Loader from '../../../../component/Loader/Loader';
import LearningPath from '../../../../component/LearningPath/LearningPath';
import { ErrorField } from '../../../../component/ErrorField/ErrorField';
import { getSuggestedCourses } from '../../../../redux/Slices/SuggestedCourseSlice';
import styles from './SuggestedCourses.module.scss';

/* eslint-disable max-len */

const SuggestedCourses = ({ userId, token }:{userId:number, token:string}) => {
  const dispatch = useDispatch();
  const { t } = useTranslation();
  const suggestedCourseState = useSelector<RootState>((state) => state.suggestedCourse);
  const { entity, isLoading, errorMessage } = suggestedCourseState as {entity:any[], isLoading:boolean, errorMessage:string};
  const {
    mainContainer, suggestedCoursesContainer, mainTitle, loaderContent,
  } = styles;
  const Title = <p className={mainTitle}>{t('string.myLearningPaths.suggestedLearningPaths')}</p>;

  useEffect(() => {
    dispatch(getSuggestedCourses({ userId, token }));
  }, []);
  if (isLoading) {
    return (
      <div className={mainContainer}>
        {Title}
        <div className={loaderContent}>
          <Loader />
        </div>
      </div>
    );
  }
  if (errorMessage) {
    return (
      <div className={mainContainer}>
        {Title}
        <ErrorField.MainErrorField className={['suggestedCourseErrorStyle']}>
          {errorMessage}
        </ErrorField.MainErrorField>
      </div>
    );
  }
  if (!entity.length) {
    return (
      <div className={mainContainer}>
        {Title}
        <h2>{t('No courses.suggested')}</h2>
      </div>
    );
  }
  return (
    <div className={mainContainer}>
      {Title}
      <div className={suggestedCoursesContainer}>
        {entity.map((suggestedCourse, index) => (
          <LearningPath
            key={index}
            courseInfo={suggestedCourse}
          />
        ))}
      </div>
    </div>
  );
};
export default SuggestedCourses;
