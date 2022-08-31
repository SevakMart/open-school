import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { RootState } from '../../redux/Store';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import Header from './Subcomponents/Header/Header';
import MainContent from './Subcomponents/MainContent/MainContent';
// import Search from '../../component/Search/Search';
import Loader from '../../component/Loader/Loader';
import userService from '../../services/userService';
import categoriesService from '../../services/categoriesService';
import { SearchedCategoryType } from '../../types/SearchedCategoryType';
import CategoryWithSubcategoriesProfile from '../../component/CategoryWithSubcategoriesProfile/CategoryWithSubcategoriesProfile';
import styles from './ChooseCategoryPage.module.scss';

const ChooseCategoryPage = ({ userInfo }:{userInfo:object}) => {
  // const userInfo = useSelector<RootState>((state) => state.userInfo);
  const subcategoryIdArray = useSelector<RootState>((state) => state.chooseSubcategories);
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [searchedCategories, setSearchedCategories] = useState<SearchedCategoryType>({});
  const [isLoading, setIsLoading] = useState(true);
  const { categoriesList, nextButton } = styles;

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
          setErrorMessage(t('error'));
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
      <Header changeUrlQueries={handleChangeUrlTitleParam} />
      <MainContent
        isLoading={isLoading}
        errorMessage={errorMessage}
        searchedCategories={searchedCategories}
        userInfoId={(userInfo as any).id}
        userInfoToken={(userInfo as any).token}
      />
    </>
  );
};
export default ChooseCategoryPage;
