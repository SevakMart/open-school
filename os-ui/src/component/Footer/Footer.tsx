import { useTranslation } from 'react-i18next';
import Button from '../Button/Button';
import styles from './Footer.module.scss';

const Footer = () => {
  const { t } = useTranslation();
  const { mainContainer, buttonContainer } = styles;
  return (
    <>
      <div className={mainContainer}>
        <h2>{t('string.homePage.footer.startJourney')}</h2>
        <div className={buttonContainer}>
          <Button.SignUpButton className={['signUpFooter', 'signUpFooter__student']}>
            {t('button.homePage.signUpStudent')}
          </Button.SignUpButton>
          <Button.SignUpButton className={['signUpFooter', 'signUpFooter__mentor']}>
            {t('button.homePage.signUpMentor')}
          </Button.SignUpButton>
        </div>
      </div>
      <h2>{t('string.homePage.navBar.logo')}</h2>
    </>
  );
};
export default Footer;
