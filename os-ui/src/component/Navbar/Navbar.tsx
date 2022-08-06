import { useTranslation } from 'react-i18next';
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
        <button type="button">{t('button.homePage.signIn')}</button>
      </div>
    </nav>
  );
};
export default Navbar;
