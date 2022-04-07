import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import Search from '../../component/Search/Search';
import { getSearchedCategories } from '../../services/getSearchedCategories';
import { CHOOSE_CATEGORIES_HEADER, GET_CATEGORY_SUBCATEGORY_SEARCH_URL, ERROR_MESSAGE } from '../../constants/Strings';
import { SearchedCategoryType } from '../../types/SearchedCategoryType';
import CategoryWithSubcategoriesProfile from '../../component/CategoryWithSubcategoriesProfile/CategoryWithSubcategoriesProfile';
import styles from './ChooseCategoryPage.module.scss';

const ChooseCategoryPage = () => {
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [searchedCategories, setSearchedCategories] = useState<SearchedCategoryType>({});
  const { mainHeader, categoriesList } = styles;

  const handleChangeUrlTitleParam = (titleParam:string) => {
    setTitle(titleParam);
  };

  useEffect(() => {
    if (!title) navigate('/choose_categories?searchCategories=all', { replace: true });
    getSearchedCategories(`${GET_CATEGORY_SUBCATEGORY_SEARCH_URL}${title}`)
      .then((data) => {
        if (!data.errorMessage) setSearchedCategories({ ...data });
        else setErrorMessage(data.errorMessage);
      });
  }, [title]);

  return (
    <>
      <NavbarOnSignIn />
      <h1 className={mainHeader}>{CHOOSE_CATEGORIES_HEADER}</h1>
      <Search pathname="/choose_categories" searchKeyName="searchCategories" changeUrlQueries={handleChangeUrlTitleParam} />
      <div className={categoriesList}>
        {
        !errorMessage ? Object.entries(searchedCategories).map((category) => (
          <CategoryWithSubcategoriesProfile
            key={category[0]}
            parentCategory={category[0]}
            subcategories={category[1]}
          />
        )) : <h2>{ERROR_MESSAGE}</h2>
      }
      </div>
    </>
  );
};
export default ChooseCategoryPage;
