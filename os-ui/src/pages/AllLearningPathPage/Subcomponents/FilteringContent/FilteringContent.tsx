import styles from './FilteringContent.module.scss';
import CheckedContent from '../CheckedContent/CheckedContent';
import { Subtype } from '../../../../types/FilteringFeaturesType';

const FilteringContent = ({ title, content, filterFeature }:
  {title:string, content:Subtype, filterFeature:string}) => {
  const {
    filteringContent, filteringSubContent,
    mainFilteringContentTitle, filteringSubContentTitle,
  } = styles;
  return (
    <div className={filteringContent}>
      <p data-testid={title} className={mainFilteringContentTitle}>{title}</p>
      {
          content && Object.entries(content).map((innerContent, index) => {
            if (typeof innerContent[1] === 'object') {
              return (
                <div className={filteringSubContent} key={index}>
                  <p
                    data-testid={innerContent[0]}
                    className={filteringSubContentTitle}
                  >
                    {innerContent[0]}

                  </p>
                  {
                    Object.entries(innerContent[1]).length
                    && Object.entries(innerContent[1]).map((subcontent, i) => (
                      <CheckedContent
                        key={i}
                        filterFeature={filterFeature}
                        id={subcontent[0]}
                        checkedContent={subcontent[1]}
                      />
                    ))
                  }
                </div>
              );
            }
            return (
              <CheckedContent
                key={index}
                filterFeature={filterFeature}
                id={innerContent[0]}
                checkedContent={innerContent[1]}
              />
            );
          })
      }
    </div>
  );
};
export default FilteringContent;
