import { useState, useEffect, useContext } from 'react';
import { useTranslation } from 'react-i18next';
import { userContext, courseContentContext } from '../../../../contexts/Contexts';
import featureService from '../../../../services/featureService';
import FilteringContent from '../FilteringContent/FilteringContent';
import { FilteringFeatureType } from '../../../../types/FilteringFeaturesType';
import { CourseContent } from '../../../../types/CourseTypes';
import styles from './FilterComponent.module.scss';

const FilterComponent = () => {
  const [filterFeatures, setFilterFeatures] = useState<FilteringFeatureType>({});
  const { t } = useTranslation();
  const { token } = useContext(userContext);
  const contentType = useContext(courseContentContext);
  const { mainContainer, filterMainContent, mainTitle } = styles;

  useEffect(() => {
    featureService.getFilterFeatures({}, token).then((data) => {
      setFilterFeatures(data);
    });
  }, []);

  return (
    <div
      className={mainContainer}
      style={contentType === CourseContent.SAVEDCOURSES ? { display: 'none' } : { display: 'flex' }}
    >
      <div className={filterMainContent}>
        <p className={mainTitle}>{t('string.learningPath.filter')}</p>
        {Object.entries(filterFeatures).length > 0
        && Object.entries(filterFeatures).map((feature, index) => {
          const title:string = feature[0] === 'parentAndSubcategories' ? t('string.learningPath.category') : feature[0] === 'allLanguages' ? t('string.learningPath.language') : t('string.learningPath.courseLevel');
          return (
            <FilteringContent
              title={title}
              key={index}
              content={feature[1]}
              filterFeature={title === t('string.learningPath.category') ? 'subCategoryIds' : title === t('string.learningPath.language') ? 'languageIds' : 'difficultyIds'}
            />
          );
        })}
      </div>
    </div>
  );
};
export default FilterComponent;
