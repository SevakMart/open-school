import { useTranslation } from 'react-i18next';
import styles from './Footer.module.scss';

const Footer = () => {
  const { t } = useTranslation();
  const {
    mainContainer, categories, becomeMentor, findUsOn,
  } = styles;
  return (
    <div className={mainContainer}>
      <button type="button">{t('string.homePage.navBar.logo')}</button>
      <div className={categories}>
        <h2>{t('string.homePage.footer.categories')}</h2>
        <p>{t('string.homePage.footer.softwareEngineering')}</p>
        <p>{t('string.homePage.footer.design')}</p>
        <p>{t('string.homePage.footer.management')}</p>
      </div>
      <div className={becomeMentor}>
        <h2>{t('string.homePage.footer.becomeMentor')}</h2>
        <p>{t('string.homePage.footer.about')}</p>
        <p>{t('string.homePage.footer.help')}</p>
      </div>
      <div className={findUsOn}>
        <h2>{t('string.homePage.footer.findUs')}</h2>
        <p>Facebook</p>
        <p>Instagram</p>
      </div>
    </div>
  );
};
export default Footer;
