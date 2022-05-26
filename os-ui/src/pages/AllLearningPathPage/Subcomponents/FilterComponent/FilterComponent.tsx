import { useState } from 'react';
import {
  CATEGORY, COURSE_LEVEL, ENGINEERING, FILTER, LANGUAGE, TECHNICAL,
} from '../../../../constants/Strings';
import styles from './FilterComponent.module.scss';

enum VisibleFilterTab {
    isVisible='visible',
    isHidden='hidden'
}

const FilterComponent = ({ changeVisibility }:{changeVisibility:()=>void}) => {
  const [visibleFilterTab, setVisibleFilterTab] = useState(VisibleFilterTab.isVisible);
  const {
    mainContainer, hiddenContainer, filterMainContent,
    filteringContent, filteringSubContent, checkedContent,
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

  return (
    <div className={visibleFilterTab === 'visible' ? mainContainer : hiddenContainer}>
      <div className={filterMainContent}>
        <p>{FILTER}</p>
        <div className={filteringContent}>
          <p>{CATEGORY}</p>
          <div className={filteringSubContent}>
            <p>{TECHNICAL}</p>
            {/* This part is going to be replace by the content coming from the backend */}
            <div className={checkedContent}>
              <input type="checkbox" name="checkbox" id="advancedEngineering" />
              <label htmlFor="advancedEngineering">Advanced Engineering</label>
            </div>
            <div className={checkedContent}>
              <input type="checkbox" name="checkbox" id="applicationsAndTools" />
              <label htmlFor="applicationsAndTools">Advanced Engineering</label>
            </div>
          </div>
          <div className={filteringSubContent}>
            <p>{ENGINEERING}</p>
            {/* This part is going to be replace by the content coming from the backend */}
            <div className={checkedContent}>
              <input type="checkbox" name="checkbox" id="advancedEngineering" />
              <label htmlFor="advancedEngineering">Advanced Engineering</label>
            </div>
            <div className={checkedContent}>
              <input type="checkbox" name="checkbox" id="applicationsAndTools" />
              <label htmlFor="applicationsAndTools">Advanced Engineering</label>
            </div>
          </div>
        </div>
        <div className={filteringContent}>
          <p>{LANGUAGE}</p>
          {/* This part is going to be replace by the content coming from the backend */}
          <div className={checkedContent}>
            <input type="checkbox" name="checkbox" id="English" />
            <label htmlFor="English">English</label>
          </div>
          <div className={checkedContent}>
            <input type="checkbox" name="checkbox" id="Russian" />
            <label htmlFor="Russian">Russian</label>
          </div>
        </div>
        <div className={filteringContent}>
          <p>{COURSE_LEVEL}</p>
          {/* This part is going to be replace by the content coming from the backend */}
          <div className={checkedContent}>
            <input type="checkbox" name="checkbox" id="Basic" />
            <label htmlFor="Basic">Basic</label>
          </div>
          <div className={checkedContent}>
            <input type="checkbox" name="checkbox" id="Intermediate" />
            <label htmlFor="Intermediate">Intermediate</label>
          </div>
          <div className={checkedContent}>
            <input type="checkbox" name="checkbox" id="Advanced" />
            <label htmlFor="Advanced">Advanced</label>
          </div>
        </div>
      </div>
      <button type="button" onClick={toggleFilterTabVisibility}>{'<'}</button>
    </div>
  );
};
export default FilterComponent;
