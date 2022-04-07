import { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import Search from '../../component/Search/Search';
import { getSearchedCategories } from '../../services/getSearchedCategories';
import { CHOOSE_CATEGORIES_HEADER, GET_CATEGORY_SUBCATEGORY_SEARCH_URL, ERROR_MESSAGE } from '../../constants/Strings';
import { SearchedCategoryType } from '../../types/SearchedCategoryType';
import CategoryWithSubcategoriesProfile from '../../component/CategoryWithSubcategoriesProfile/CategoryWithSubcategoriesProfile';
import styles from './ChooseCategoryPage.module.scss';

const ChooseCategoryPage = () => {
  const { search } = useLocation();
  const navigate = useNavigate();
  const categoryOrSubcategoryTitle = new URLSearchParams(search).get('searchCategories')
    ? new URLSearchParams(search).get('searchCategories') : '';
  const [title, setTitle] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [searchedCategories, setSearchedCategories] = useState<SearchedCategoryType>({});
  // const [subcategoryArray, setSubcategoryArray] = useState<Array<number>>([]);
  const { mainHeader, categoriesList } = styles;

  const handleChangeUrlTitleParam = (titleParam:string) => {
    setTitle(titleParam);
  };

  /* const handleAddingSubcategory = (subcategoryId:number) => {
    if (!subcategoryArray.some((id) => id === subcategoryId)) {
      setSubcategoryArray([...subcategoryArray, subcategoryId]);
    }
  };

  const handleRemovingSubcategory = (subcategoryId:number) => {
    const subcategoryIdIndex = subcategoryArray.findIndex((id) => id === subcategoryId);
    subcategoryArray.splice(subcategoryIdIndex, 1);
    setSubcategoryArray([...subcategoryArray]);
  }; */

  useEffect(() => {
    if (!title) navigate('/choose_categories?searchCategories=all', { replace: true });
    getSearchedCategories(`${GET_CATEGORY_SUBCATEGORY_SEARCH_URL}${title}`)
      .then((data) => {
        if (!data.errorMessage) setSearchedCategories({ ...data });
        else setErrorMessage(data.errorMessage);
      });
  }, [title]);
  // console.log(searchedCategories);
  // console.log(subcategoryArray);
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
            /* addSubcategory={handleAddingSubcategory}
            removeSubcategory={handleRemovingSubcategory} */
          />
        )) : <h2>{ERROR_MESSAGE}</h2>
      }
      </div>
    </>
  );
};
export default ChooseCategoryPage;
