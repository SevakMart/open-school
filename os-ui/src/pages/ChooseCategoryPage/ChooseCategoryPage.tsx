import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { RootState } from '../../redux/Store';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import Search from '../../component/Search/Search';
import Loader from '../../component/Loader/Loader';
import userService from '../../services/userService';
import categoriesService from '../../services/categoriesService';
import { SearchedCategoryType } from '../../types/SearchedCategoryType';
import CategoryWithSubcategoriesProfile from '../../component/CategoryWithSubcategoriesProfile/CategoryWithSubcategoriesProfile';
import styles from './ChooseCategoryPage.module.scss';

const ChooseCategoryPage = () => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const subcategoryIdArray = useSelector<RootState>((state) => state.chooseSubcategories);
  const { t } = useTranslation();
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
    userService.savePreferredCategories(
      (userInfo as any).id,
      (userInfo as any).token,
      subcategoryIdArray as Array<number>,
    ).then(() => navigate('/myLearningPath'));
  };
  useEffect(() => {
    let cancel = false;
    categoriesService.getSearchedCategories({ title }, (userInfo as any).token)
      .then((data) => {
        if (cancel) return;
        if (!Object.entries(data).length) {
          setErrorMessage(t('messages.noData.default'));
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
      <div className={mainHeader}>
        <h1>{t('string.homePage.header.chooseCategories')}</h1>
        <Search changeUrlQueries={handleChangeUrlTitleParam} />
      </div>
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
      <button className={nextButton} type="button" onClick={handleSavingCategories}>{t('button.chooseCategories.next')}</button>
    </>
  );
};
export default ChooseCategoryPage;
