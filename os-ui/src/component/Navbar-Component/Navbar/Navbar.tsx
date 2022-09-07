import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import Button from '../../Button/Button';
import { Types } from '../../../types/types';
import { openModal } from '../../../redux/Slices/PortalOpenStatus';
import styles from './Navbar.module.scss';

/* eslint-disable max-len */

const Navbar = () => {
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const { navContainer, categoriesMentors, buttonContent } = styles;

  const handleSignIn = () => {
    dispatch(openModal({ buttonType: Types.Button.SIGN_IN }));
  };
  const goToExploreCategoriesPage = () => {
    dispatch(openModal({ buttonType: Types.Button.SIGN_IN }));
  };
  const goToMentorsPage = () => {
    dispatch(openModal({ buttonType: Types.Button.SIGN_IN, isRequestForMentorsPage: true }));
  };

  return (
    <nav className={navContainer}>
      <h2>{t('string.homePage.navBar.logo')}</h2>
      <div className={categoriesMentors}>
        <p onClick={goToExploreCategoriesPage}>{t('string.homePage.navBar.exploreCategories')}</p>
        <p onClick={goToMentorsPage}>{t('string.homePage.navBar.mentors')}</p>
      </div>
      <div className={buttonContent}>
        <p>{t('button.homePage.becomeMentor')}</p>
        <Button.SignInButton className={['navbarSignInButton']} onClick={handleSignIn}>
          {t('button.homePage.signIn')}
        </Button.SignInButton>
      </div>
    </nav>
  );
};
export default Navbar;
