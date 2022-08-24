import { useTranslation } from 'react-i18next';
import Button from '../Button/Button';
import styles from './Navbar.module.scss';

const Navbar = () => {
  const { t } = useTranslation();
  const { navContainer, categoriesMentors, buttonContent } = styles;
  return (
    <nav className={navContainer}>
      <h2>{t('string.homePage.navBar.logo')}</h2>
      <div className={categoriesMentors}>
        <p>{t('string.homePage.navBar.exploreCategories')}</p>
        <p>{t('string.homePage.navBar.mentors')}</p>
      </div>
      <div className={buttonContent}>
        <p>{t('button.homePage.becomeMentor')}</p>
        <Button.SignInButton className={['navbarSignInButton']}>
          {t('button.homePage.signIn')}
        </Button.SignInButton>
      </div>
    </nav>
  );
};
export default Navbar;
