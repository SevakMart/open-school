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
    mainContainer, suggestedCoursesContainer, mainTitle, noSuggestedCourses,
  } = styles;
  const Title = <p className={mainTitle}>{t('string.myLearningPaths.suggestedLearningPaths')}</p>;
  const NoSuggestedCourses = <h2>{t('No courses.suggested')}</h2>;

  useEffect(() => {
    dispatch(getSuggestedCourses({ userId, token }));
  }, []);

  return (
    <div className={mainContainer}>
      {Title}
      <div className={entity.length > 0 ? suggestedCoursesContainer : noSuggestedCourses}>
        {isLoading && <Loader />}
        {errorMessage !== '' && (
          <ErrorField.MainErrorField className={['suggestedCourseErrorStyle']}>
            {errorMessage}
          </ErrorField.MainErrorField>
        )}
        {entity.length === 0 && !isLoading && errorMessage.length === 0 && NoSuggestedCourses }
        {entity.length > 0 && entity.map((suggestedCourse, index) => (
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
