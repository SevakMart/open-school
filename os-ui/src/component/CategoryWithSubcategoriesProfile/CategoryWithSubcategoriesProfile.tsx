import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import { addSubcategory, removeSubcategory } from '../../redux/Slices/ChoosSubcategorySlice';
import { CategoryWithSubcategoriesProfilePropTypes } from '../../types/CategoryWithSubcategoriesProfilePropTypes';
import styles from './CategoryWithSubcategoriesProfile.module.scss';

const CategoryWithSubcategoriesProfile = ({
  parentCategory, subcategories,
}:
    CategoryWithSubcategoriesProfilePropTypes) => {
  const subcategoryIdArray = useSelector<RootState>((state) => state.chooseSubcategories);
  const dispatch = useDispatch();
  const { mainContent, subcategoryContent, subMainContent } = styles;
  const handleChange = (e:React.SyntheticEvent) => {
    if ((e.target as HTMLInputElement).checked) {
      dispatch(addSubcategory(+(e.target as HTMLInputElement).id));
    } else dispatch(removeSubcategory(+(e.target as HTMLInputElement).id));
  };

  return (
    <div className={mainContent}>
      <h3 data-testid={parentCategory}>{parentCategory}</h3>
      <div className={subMainContent}>
        {
            subcategories.length > 0 ? subcategories.map((subcategory) => (
              <div className={subcategoryContent} key={+subcategory.id}>
                {
                  (subcategoryIdArray as Array<number>).some((id) => id === +subcategory.id)
                    ? <input type="checkbox" id={`${subcategory.id}`} onChange={handleChange} checked />
                    : <input type="checkbox" id={`${subcategory.id}`} onChange={handleChange} checked={false} />
                }
                <label data-testid={subcategory.title} htmlFor={`${subcategory.id}`}>{subcategory.title}</label>
              </div>
            )) : null
        }
      </div>
    </div>
  );
};
export default CategoryWithSubcategoriesProfile;
