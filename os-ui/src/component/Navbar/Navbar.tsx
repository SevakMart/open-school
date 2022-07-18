import { useTranslation } from 'react-i18next';
import styles from './Navbar.module.scss';

const Navbar = () => {
  const { t } = useTranslation();
  const {
    navContainer, navMainContent, categoriesMentors, buttonContent,
  } = styles;
  return (
    <nav className={navContainer}>
      <h2>{t('string.homePage.navBar.logo')}</h2>
      <div className={navMainContent}>
        <div className={categoriesMentors}>
          <p>{t('string.homePage.navBar.exploreCategories')}</p>
          <p>{t('string.homePage.navBar.mentors')}</p>
        </div>
        <div className={buttonContent}>
          <button type="button">{t('button.homePage.becomeMentor')}</button>
          <button type="button">{t('button.homePage.signIn')}</button>
        </div>
      </div>
    </nav>
  );
};
export default Navbar;
