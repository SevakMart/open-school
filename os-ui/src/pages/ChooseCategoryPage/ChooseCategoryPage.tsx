import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import Search from '../../component/Search/Search';
import Loader from '../../component/Loader/Loader';
import categoriesService from '../../services/categoriesService';
import { getSearchedCategories } from '../../services/getSearchedCategories';
import { savePreferredCategories } from '../../services/savePreferredCategories';
import {
  CHOOSE_CATEGORIES_HEADER,
  GET_CATEGORY_SUBCATEGORY_SEARCH_URL,
  SAVE_PREFERRED_CATEGORIES,
  EMPTY_DATA_ERROR_MESSAGE,
} from '../../constants/Strings';
import { SearchedCategoryType } from '../../types/SearchedCategoryType';
import CategoryWithSubcategoriesProfile from '../../component/CategoryWithSubcategoriesProfile/CategoryWithSubcategoriesProfile';
import styles from './ChooseCategoryPage.module.scss';

const ChooseCategoryPage = () => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const subcategoryIdArray = useSelector<RootState>((state) => state.chooseSubcategories);
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [searchedCategories, setSearchedCategories] = useState<SearchedCategoryType>({});
  const [isLoading, setIsLoading] = useState(true);
  const { mainHeader, categoriesList, nextButton } = styles;

  const handleChangeUrlTitleParam = (titleParam:string) => {
    setTitle(titleParam);
  };
  const handleSavingCategories = () => {
    savePreferredCategories(
      `${SAVE_PREFERRED_CATEGORIES}/${(userInfo as any).id}/categories`,
      (userInfo as any).token,
      subcategoryIdArray as Array<number>,
    ).then(() => navigate('/myLearningPath'));
  };
  useEffect(() => {
    let cancel = false;
    if (!title) navigate('/categories/subcategories?searchCategories=all', { replace: true });
    categoriesService.getSearchedCategories({ title })
      .then((data) => {
        if (cancel) return;
        if (!Object.entries(data).length) {
          setErrorMessage(EMPTY_DATA_ERROR_MESSAGE);
          setIsLoading(false);
        } else if (!data.errorMessage) {
          setSearchedCategories({ ...data });
          setIsLoading(false);
          setErrorMessage('');
        } else {
          setErrorMessage(data.errorMessage);
          setIsLoading(false);
        }
      });
    return () => { cancel = true; };
  }, [title]);
  return (
    <>
      <NavbarOnSignIn />
      <h1 className={mainHeader}>{CHOOSE_CATEGORIES_HEADER}</h1>
      <Search pathname="/categories/subcategories" searchKeyName="searchCategories" changeUrlQueries={handleChangeUrlTitleParam} />
      <div className={categoriesList}>
        {
          isLoading ? <Loader />
            : !errorMessage ? Object.entries(searchedCategories).map((category) => (
              <CategoryWithSubcategoriesProfile
                key={category[0]}
                parentCategory={category[0]}
                subcategories={category[1]}
              />
            )) : <h2 data-testid="chooseSubcategoriesErrorMessage">{errorMessage}</h2>
      }
      </div>
      <button className={nextButton} type="button" onClick={handleSavingCategories}>NEXT</button>
    </>
  );
};
export default ChooseCategoryPage;
