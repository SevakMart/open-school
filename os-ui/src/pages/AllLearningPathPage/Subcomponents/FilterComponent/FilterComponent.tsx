import { useState, useEffect, useContext } from 'react';
import { tokenContext } from '../../../../contexts/Contexts';
import courseService from '../../../../services/courseService';
import FilteringContent from '../FilteringContent/FilteringContent';
import { FilteringFeatureType } from '../../../../types/FilteringFeaturesType';
import { FILTER } from '../../../../constants/Strings';
import styles from './FilterComponent.module.scss';

enum VisibleFilterTab {
    isVisible='visible',
    isHidden='hidden'
}

const FilterComponent = ({ changeVisibility }:{changeVisibility:()=>void}) => {
  const [visibleFilterTab, setVisibleFilterTab] = useState(VisibleFilterTab.isVisible);
  const [filterFeatures, setFilterFeatures] = useState<FilteringFeatureType>({});
  const token = useContext(tokenContext);
  const {
    mainContainer, hiddenContainer, filterMainContent, mainTitle, visibilityButton,
  } = styles;

  const toggleFilterTabVisibility = () => {
    if (visibleFilterTab === VisibleFilterTab.isVisible) {
      setVisibleFilterTab(VisibleFilterTab.isHidden);
      changeVisibility();
    } else {
      setVisibleFilterTab(VisibleFilterTab.isVisible);
      changeVisibility();
    }
  };

  useEffect(() => {
    courseService.getFilterFeatures({}, token).then((data) => {
      setFilterFeatures(data);
    });
  }, []);

  return (
    <div className={visibleFilterTab === 'visible' ? mainContainer : hiddenContainer}>
      <div className={filterMainContent}>
        <p className={mainTitle}>{FILTER}</p>
        {
          Object.entries(filterFeatures).length
            ? Object.entries(filterFeatures).map((feature, index) => {
              const title:string = feature[0] === 'parentAndRelevantChildCategories' ? 'Category' : feature[0] === 'allLanguages' ? 'Language' : 'Course Level';
              return (
                <FilteringContent
                  title={title}
                  key={index}
                  content={feature[1]}
                />
              );
            }) : null
        }
      </div>
      <button className={visibilityButton} type="button" onClick={toggleFilterTabVisibility}>{'<'}</button>
    </div>
  );
};
export default FilterComponent;
