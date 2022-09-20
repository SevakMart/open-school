import { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import LearningPath from '../../../../component/LearningPath/LearningPath';
import { getSuggestedCourses } from '../../../../redux/Slices/SuggestedCourseSlice';
import ContentRenderer from '../../../../component/ContentRenderer/ContentRenderer';
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

  useEffect(() => {
    dispatch(getSuggestedCourses({ userId, token }));
  }, []);

  return (
    <div className={mainContainer}>
      {Title}
      <div className={entity.length > 0 ? suggestedCoursesContainer : noSuggestedCourses}>
        <ContentRenderer
          isLoading={isLoading}
          errorMessage={errorMessage}
          entity={entity}
          errorFieldClassName="suggestedCourseErrorStyle"
          render={(entity) => (
            entity.map((suggestedCourse, index) => (
              <LearningPath
                key={index}
                courseInfo={suggestedCourse}
              />
            ))
          )}
        />
      </div>
    </div>
  );
};
export default SuggestedCourses;
