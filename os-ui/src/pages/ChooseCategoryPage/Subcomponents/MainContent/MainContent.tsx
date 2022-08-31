import CategoryWithSubcategoriesProfile from '../CategoryWithSubcategoriesProfile/CategoryWithSubcategoriesProfile';
import Loader from '../../../../component/Loader/Loader';
import { SearchedCategoryType } from '../../../../types/SearchedCategoryType';
import { ErrorField } from '../../../../component/ErrorField/ErrorField';
import Button from '../../../../component/Button/Button';
import styles from './MainContent.module.scss';

/* eslint-disable max-len */

const MainContent = ({
  isLoading, errorMessage, searchedCategories, userInfoId, userInfoToken,
}:{isLoading:boolean, errorMessage:string, searchedCategories:SearchedCategoryType, userInfoId:number, userInfoToken:string}) => {
  const { categoriesList } = styles;
  return (
    <>
      <div className={categoriesList}>
        {isLoading && <Loader />}
        {!isLoading && errorMessage !== '' && <ErrorField.MainErrorField className={['subcategoriesErrorField']}>{errorMessage}</ErrorField.MainErrorField>}
        {!isLoading && errorMessage === '' && (
          Object.entries(searchedCategories).map((category) => (
            <CategoryWithSubcategoriesProfile
              key={category[0]}
              parentCategory={category[0]}
              subcategories={category[1]}
            />
          ))
        )}
      </div>
      {/* <button className={nextButton} type="button" onClick={handleSavingCategories}>{t('button.chooseCategories.next')}</button> */}
    </>
  );
};
export default MainContent;
