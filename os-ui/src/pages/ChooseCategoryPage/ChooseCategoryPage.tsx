import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import Header from './Subcomponents/Header/Header';
import MainContent from './Subcomponents/MainContent/MainContent';
import Button from '../../component/Button/Button';
import categoriesService from '../../services/categoriesService';
import { SearchedCategoryType } from '../../types/SearchedCategoryType';
import styles from './ChooseCategoryPage.module.scss';
import NavbarOnSignIn from '../../component/Navbar-Component/NavbarOnSignIn/NavbarOnSignIn';

/* eslint-disable max-len */

const ChooseCategoryPage = ({ userInfo }:{userInfo:object}) => {
  const { t } = useTranslation();
  const [title, setTitle] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [searchedCategories, setSearchedCategories] = useState<SearchedCategoryType>({});
  const [isLoading, setIsLoading] = useState(true);
  const { buttonContainer } = styles;
  const handleChangeUrlTitleParam = (titleParam:string) => {
    setTitle(titleParam);
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
      <NavbarOnSignIn />
      <Header changeUrlQueries={handleChangeUrlTitleParam} />
      <MainContent
        isLoading={isLoading}
        errorMessage={errorMessage}
        searchedCategories={searchedCategories}
      />
      <div className={buttonContainer}>
        <Button.NextButton userInfoId={(userInfo as any).id} userInfoToken={(userInfo as any).token} className={['nextButton']}>
          {t('button.chooseCategories.next')}
        </Button.NextButton>
      </div>
    </>
  );
};
export default ChooseCategoryPage;
