import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { useSelector } from 'react-redux';
import { userContext } from '../../contexts/Contexts';
import { RootState } from '../../redux/Store';
import HomepageMainImage from '../../assets/svg/HomepageMainImage.svg';
import styles from './HomepageWhenSignIn.module.scss';
import NavbarOnSignIn from '../../component/Navbar-Component/NavbarOnSignIn/NavbarOnSignIn';
import HomepageCategories from '../Homepage/Subcomponents/Categories/Categories';
import HomepageMentors from '../Homepage/Subcomponents/Mentors/Mentors';
import Footer from '../../component/Footer/Footer';

const HomepageWhenSignIn = () => {
  const { t } = useTranslation();
  const userInfoState = useSelector<RootState>((state) => state.userInfo);
  const { userInfo } = userInfoState as any;
  const { buttonContainer } = styles;

  const {
    mainContent, leftContent, rightContent, allTheContent,
  } = styles;

  const idAndToken = useMemo(() => ({
    token: userInfo ? (userInfo as any).token : '',
    id: userInfo ? (userInfo as any).id : -1,
  }), [{ ...userInfo }]);

  return (
    <div className={allTheContent}>
      <NavbarOnSignIn currentUserEnrolled />
      <div className={mainContent}>
        <div className={leftContent}>
          <h1>{t('string.homePage.header.educationalPlatform')}</h1>
          <div className={buttonContainer} />
        </div>
        <div className={rightContent}>
          <img src={HomepageMainImage} alt="Education platform logo" />
        </div>
      </div>
      <userContext.Provider value={idAndToken}>
        <HomepageCategories />
        <HomepageMentors />
      </userContext.Provider>
      <Footer />
    </div>

  );
};
export default HomepageWhenSignIn;
