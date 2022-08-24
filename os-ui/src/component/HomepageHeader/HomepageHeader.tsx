import { useTranslation } from 'react-i18next';
import Navbar from '../Navbar/Navbar';
import HomepageMainImage from '../../assets/svg/HomepageMainImage.svg';
import Button from '../Button/Button';
import styles from './HomepageHeader.module.scss';

const HomepageHeader = ({ handleFormVisibility }:
  {handleFormVisibility(buttonType:string):void}) => {
  const { t } = useTranslation();
  const {
    mainContent, leftContent, rightContent, buttonContainer,
  } = styles;

  return (
    <>
      <Navbar />
      <div className={mainContent}>
        <div className={leftContent}>
          <h1>{t('string.homePage.header.educationalPlatform')}</h1>
          <p>{t('string.homePage.header.chooseCategories')}</p>
          <div className={buttonContainer}>
            <Button.SignUpButton className={['headerButton', 'headerButton__signUp']}>
              {t('button.homePage.signUp')}
            </Button.SignUpButton>
            <Button.SignInButton className={['headerButton', 'headerButton__signIn']}>
              {t('button.homePage.signIn')}
            </Button.SignInButton>
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
