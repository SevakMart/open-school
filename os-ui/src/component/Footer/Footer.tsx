import { useTranslation } from 'react-i18next';
import styles from './Footer.module.scss';

const Footer = () => {
  const { t } = useTranslation();
  const { mainContainer, buttonContainer } = styles;
  return (
    <>
      <div className={mainContainer}>
        <h2>{t('string.homePage.footer.startJourney')}</h2>
        <div className={buttonContainer}>
          <button type="button">{t('button.homePage.signUpStudent')}</button>
          <button type="button">{t('button.homePage.signUpMentor')}</button>
        </div>
      </div>
      <h2>{t('string.homePage.navBar.logo')}</h2>
    </>
  );
};
export default Footer;
