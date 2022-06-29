import { useState, useEffect, useContext } from 'react';
import { useTranslation } from 'react-i18next';
import { userContext, courseContentContext } from '../../../../contexts/Contexts';
import featureService from '../../../../services/featureService';
import FilteringContent from '../FilteringContent/FilteringContent';
import { FilteringFeatureType } from '../../../../types/FilteringFeaturesType';
import { FILTER } from '../../../../constants/Strings';
import { CourseContent } from '../../../../types/CourseContent';
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
        <p className={mainTitle}>{FILTER}</p>
        {
          Object.entries(filterFeatures).length
            ? Object.entries(filterFeatures).map((feature, index) => {
              const title:string = feature[0] === 'parentAndSubcategories' ? t('Category') : feature[0] === 'allLanguages' ? t('Language') : t('Course Level');
              return (
                <FilteringContent
                  title={title}
                  key={index}
                  content={feature[1]}
                  filterFeature={title === t('Category') ? 'subCategoryIds' : title === t('Language') ? 'languageIds' : 'difficultyIds'}
                />
              );
            }) : null
        }
      </div>
    </div>
  );
};
export default FilterComponent;
