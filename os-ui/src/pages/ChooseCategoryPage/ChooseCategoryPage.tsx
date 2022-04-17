import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import { removeLoggedInUser } from '../../redux/Slices/loginUserSlice';
import Search from '../../component/Search/Search';
import Loader from '../../component/Loader/Loader';
import { getSearchedCategories } from '../../services/getSearchedCategories';
import { savePreferredCategories } from '../../services/savePreferredCategories';
import {
  CHOOSE_CATEGORIES_HEADER,
  GET_CATEGORY_SUBCATEGORY_SEARCH_URL,
  ERROR_MESSAGE,
  SAVE_PREFERRED_CATEGORIES,
} from '../../constants/Strings';
import { SearchedCategoryType } from '../../types/SearchedCategoryType';
import CategoryWithSubcategoriesProfile from '../../component/CategoryWithSubcategoriesProfile/CategoryWithSubcategoriesProfile';
import styles from './ChooseCategoryPage.module.scss';

const ChooseCategoryPage = () => {
  const dispatch = useDispatch();
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
  const handleSignOut = () => {
    dispatch(removeLoggedInUser());
    navigate('/homepage');
  };
  const handleSavingCategories = () => {
    savePreferredCategories(
      `${SAVE_PREFERRED_CATEGORIES}`,
      (userInfo as any).id,
      (userInfo as any).token,
      subcategoryIdArray as Array<number>,
    );
  };
  useEffect(() => {
    let cancel = false;
    if (!title) navigate(`/choose_categories/userId=${(userInfo as any).id}?searchCategories=all`, { replace: true });
    getSearchedCategories(`${GET_CATEGORY_SUBCATEGORY_SEARCH_URL}${title}`)
      .then((data) => {
        if (cancel) return;
        if (!data.errorMessage) {
          setSearchedCategories({ ...data });
          setIsLoading(false);
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
      <button className={nextButton} type="button" onClick={handleSavingCategories}>NEXT</button>
      <button type="button" onClick={handleSignOut}>Sign out</button>
    </>
  );
};
export default ChooseCategoryPage;
