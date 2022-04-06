import { CategoryWithSubcategoriesProfilePropTypes } from '../../types/CategoryWithSubcategoriesProfilePropTypes';
import styles from './CategoryWithSubcategoriesProfile.module.scss';

const CategoryWithSubcategoriesProfile = ({ parentCategory, subcategories }:
    CategoryWithSubcategoriesProfilePropTypes) => {
  const { mainContent, subcategoryContent } = styles;
  return (
    <div className={mainContent}>
      <h3>{parentCategory}</h3>
      {
            subcategories.length > 0 ? subcategories.map((subcategory, index) => (
              <div className={subcategoryContent} key={index}>
                <input type="checkbox" value={subcategory.id} id={`${subcategory.id}`} />
                <label htmlFor={`${subcategory.id}`}>{subcategory.title}</label>
              </div>
            )) : null
        }
    </div>
  );
};
export default CategoryWithSubcategoriesProfile;
