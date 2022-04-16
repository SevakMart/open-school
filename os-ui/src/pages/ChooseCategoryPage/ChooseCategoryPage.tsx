import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import Search from '../../component/Search/Search';
import Loader from '../../component/Loader/Loader';
import { getSearchedCategories } from '../../services/getSearchedCategories';
import { CHOOSE_CATEGORIES_HEADER, GET_CATEGORY_SUBCATEGORY_SEARCH_URL, ERROR_MESSAGE } from '../../constants/Strings';
import { SearchedCategoryType } from '../../types/SearchedCategoryType';
import CategoryWithSubcategoriesProfile from '../../component/CategoryWithSubcategoriesProfile/CategoryWithSubcategoriesProfile';
import styles from './ChooseCategoryPage.module.scss';

const ChooseCategoryPage = () => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [searchedCategories, setSearchedCategories] = useState<SearchedCategoryType>({});
  const [isLoading, setIsLoading] = useState(true);
  const { mainHeader, categoriesList } = styles;

  const handleChangeUrlTitleParam = (titleParam:string) => {
    setTitle(titleParam);
  };

  useEffect(() => {
    if (!title) navigate(`/choose_categories/userId=${(userInfo as any).id}?searchCategories=all`, { replace: true });
    getSearchedCategories(`${GET_CATEGORY_SUBCATEGORY_SEARCH_URL}${title}`)
      .then((data) => {
        if (!data.errorMessage) {
          setSearchedCategories({ ...data });
          setIsLoading(false);
        } else {
          setErrorMessage(data.errorMessage);
          setIsLoading(false);
        }
      });
  }, [title]);

  return (
    <>
      <NavbarOnSignIn />
      <h1 className={mainHeader}>{CHOOSE_CATEGORIES_HEADER}</h1>
      <Search pathname={`/choose_categories/userId=${(userInfo as any).id}`} searchKeyName="searchCategories" changeUrlQueries={handleChangeUrlTitleParam} />
      <div className={categoriesList}>
        {
          isLoading ? <Loader />
            : !errorMessage ? Object.entries(searchedCategories).map((category) => (
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
