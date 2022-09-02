import { useState, useEffect } from 'react';
import CheckedSubcategory from '../../../../assets/svg/CategoriesChecked.svg';
import UncheckedSubcategory from '../../../../assets/svg/CategoriesUnchecked.svg';
import { addSearchQueries, deleteSpecificSearchQuery, QueryHasKey } from '../../../../services/queryParams/queryParams';
import styles from './SubcategoryContent.module.scss';
/* eslint-disable max-len */

const SubcategoryContent = ({ subcategoryItem }:{subcategoryItem:{id:number, title:string}}) => {
  const [isChecked, setIsChecked] = useState(QueryHasKey(subcategoryItem.title));

  const handleCheckingSubcategory = () => {
    setIsChecked((prevState) => !prevState);
  };

  useEffect(() => {
    if (isChecked) {
      addSearchQueries(subcategoryItem.title, subcategoryItem.id);
    } else {
      deleteSpecificSearchQuery(subcategoryItem.title);
    }
  }, [isChecked]);

  return (
    <div>
      <div onClick={handleCheckingSubcategory}>
        <img src={isChecked ? CheckedSubcategory : UncheckedSubcategory} alt="logo" />
      </div>
      <p>{subcategoryItem.title}</p>
    </div>
  );
};
export default SubcategoryContent;
