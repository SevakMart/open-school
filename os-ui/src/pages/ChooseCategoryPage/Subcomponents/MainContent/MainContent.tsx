import CategoryWithSubcategoriesProfile from '../CategoryWithSubcategoriesProfile/CategoryWithSubcategoriesProfile';
import Loader from '../../../../component/Loader/Loader';
import { SearchedCategoryType } from '../../../../types/SearchedCategoryType';
import { ErrorField } from '../../../../component/ErrorField/ErrorField';
import styles from './MainContent.module.scss';

/* eslint-disable max-len */

const MainContent = ({ isLoading, errorMessage, searchedCategories }:{isLoading:boolean, errorMessage:string, searchedCategories:SearchedCategoryType}) => {
  const { categoriesList } = styles;
  return (
    <>
      <div className={categoriesList}>
        {isLoading && <Loader />}
        {errorMessage !== '' && <ErrorField.MainErrorField className={['subcategoriesErrorField']}>{errorMessage}</ErrorField.MainErrorField>}
        {errorMessage === '' && (
          Object.entries(searchedCategories).map((category) => (
            <CategoryWithSubcategoriesProfile
              key={category[0]}
              parentCategory={category[0]}
              subcategories={category[1]}
            />
          ))
        )}
      </div>
    </>
  );
};
export default MainContent;
