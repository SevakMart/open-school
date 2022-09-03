import { CategoryWithSubcategoriesProfilePropTypes } from '../../../../types/CategoryWithSubcategoriesProfilePropTypes';
import SubcategoryList from '../SubcategoryList/SubcategoryList';
import styles from './CategoryWithSubcategoriesProfile.module.scss';

/* eslint-disable max-len */

const CategoryWithSubcategoriesProfile = ({ parentCategory, subcategories }:CategoryWithSubcategoriesProfilePropTypes) => {
  const { mainContent } = styles;

  return (
    <div className={mainContent}>
      <h3 data-testid={parentCategory}>{parentCategory}</h3>
      <SubcategoryList subcategories={subcategories} />
    </div>
  );
};
export default CategoryWithSubcategoriesProfile;
