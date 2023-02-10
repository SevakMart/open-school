import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import { Types } from '../../../../types/types';
import { openModal } from '../../../../redux/Slices/PortalOpenStatus';
import Navbar from '../../../../component/Navbar-Component/Navbar/Navbar';
import HomepageMainImage from '../../../../assets/svg/HomepageMainImage.svg';
import Button from '../../../../component/Button/Button';
import styles from './Header.module.scss';

const HomepageHeader = () => {
  const { t } = useTranslation();
  const dispatch = useDispatch();

  const {
    mainContent, leftContent, rightContent, buttonContainer,
  } = styles;

  const handleSignUp = () => {
    dispatch(openModal({ buttonType: Types.Button.SIGN_UP }));
  };

  const handleSignIn = () => {
    dispatch(openModal({ buttonType: Types.Button.SIGN_IN }));
  };

  return (
    <>
      <Navbar />
      <div className={mainContent}>
        <div className={leftContent}>
          <h1>{t('string.homePage.header.educationalPlatform')}</h1>
          <p>{t('string.homePage.header.chooseCategories')}</p>
          <div className={buttonContainer}>
            <>
              <Button.SignUpButton className={['headerButton', 'headerButton__signUp']} onClick={handleSignUp}>
                {t('button.homePage.signUp')}
              </Button.SignUpButton>
              <Button.SignInButton className={['headerButton', 'headerButton__signIn']} onClick={handleSignIn}>
                {t('button.homePage.signIn')}
              </Button.SignInButton>
            </>
          </div>
        </div>
        <div className={rightContent}>
          <img src={HomepageMainImage} alt="Education platform logo" />
        </div>
      </div>
    </>
  );
};
export default HomepageHeader;
