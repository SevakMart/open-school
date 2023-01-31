import {
  useEffect, useMemo, useState,
} from 'react';
import { useTranslation } from 'react-i18next';
import { useSelector } from 'react-redux';
import { userContext } from '../../contexts/Contexts';
import { RootState } from '../../redux/Store';
import { SearchedCategoryType } from '../../types/SearchedCategoryType';
import HomepageMainImage from '../../assets/svg/HomepageMainImage.svg';
import styles from './HomepageWhenSignIn.module.scss';
import NavbarOnSignIn from '../../component/Navbar-Component/NavbarOnSignIn/NavbarOnSignIn';
import MainContent from '../ChooseCategoryPage/Subcomponents/MainContent/MainContent';
import categoriesService from '../../services/categoriesService';
import HomepageCategories from '../Homepage/Subcomponents/Categories/Categories';
import HomepageMentors from '../Homepage/Subcomponents/Mentors/Mentors';
import Footer from '../../component/Footer/Footer';

const HomepageWhenSignIn = () => {
  const { t } = useTranslation();
  const userInfoState = useSelector<RootState>((state) => state.userInfo);
  const { userInfo } = userInfoState as any;
  const [title] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [searchedCategories, setSearchedCategories] = useState<SearchedCategoryType>({});
  const [isLoading, setIsLoading] = useState(true);
  const { buttonContainer } = styles;

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

  const {
    mainContent, leftContent, rightContent,
  } = styles;

  const idAndToken = useMemo(() => ({
    token: userInfo ? (userInfo as any).token : '',
    id: userInfo ? (userInfo as any).id : -1,
  }), [{ ...userInfo }]);

  return (
    <>
      <NavbarOnSignIn />
      <div className={mainContent}>
        <div className={leftContent}>
          <h1>{t('string.homePage.header.educationalPlatform')}</h1>
          <div className={buttonContainer} />
        </div>
        <div className={rightContent}>
          <img src={HomepageMainImage} alt="Education platform logo" />
        </div>
      </div>
      <MainContent
        isLoading={isLoading}
        errorMessage={errorMessage}
        searchedCategories={searchedCategories}
      />
      <userContext.Provider value={idAndToken}>
        <HomepageCategories />
        <HomepageMentors />
      </userContext.Provider>
      <Footer />
    </>

  );
};
export default HomepageWhenSignIn;
