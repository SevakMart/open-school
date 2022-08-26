import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import Button from '../Button/Button';
import { Types } from '../../types/types';
import { openModal } from '../../redux/Slices/PortalOpenStatus';
import styles from './Footer.module.scss';

const Footer = () => {
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const { mainContainer, buttonContainer } = styles;

  const handleSignUp = () => {
    dispatch(openModal(Types.Button.SIGN_UP));
  };

  return (
    <>
      <div className={mainContainer}>
        <h2>{t('string.homePage.footer.startJourney')}</h2>
        <div className={buttonContainer}>
          <Button.SignUpButton className={['signUpFooter', 'signUpFooter__student']} onClick={handleSignUp}>
            {t('button.homePage.signUpStudent')}
          </Button.SignUpButton>
          <Button.SignUpButton className={['signUpFooter', 'signUpFooter__mentor']} onClick={handleSignUp}>
            {t('button.homePage.signUpMentor')}
          </Button.SignUpButton>
        </div>
      </div>
      <h2>{t('string.homePage.navBar.logo')}</h2>
    </>
  );
};
export default Footer;
