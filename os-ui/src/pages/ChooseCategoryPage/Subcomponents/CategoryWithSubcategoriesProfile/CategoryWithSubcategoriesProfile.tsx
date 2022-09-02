import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import { addSubcategory, removeSubcategory } from '../../../../redux/Slices/ChoosSubcategorySlice';
import { CategoryWithSubcategoriesProfilePropTypes } from '../../../../types/CategoryWithSubcategoriesProfilePropTypes';
import SubcategoryList from '../SubcategoryList/SubcategoryList';
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
      <SubcategoryList subcategories={subcategories} />
    </div>
  );
};
export default CategoryWithSubcategoriesProfile;
