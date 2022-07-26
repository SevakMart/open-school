import { useTranslation } from 'react-i18next';
import Navbar from '../Navbar/Navbar';
import styles from './HomepageHeader.module.scss';
import {
  EDUCATION_PLATFORM_IMAGE,
} from '../../constants/Strings';
import Button from '../Button/Button';

const HomepageHeader = ({ handleFormVisibility }:
  {handleFormVisibility(buttonType:string):void}) => {
  const { t } = useTranslation();
  const {
    headerContainer, mainContent, leftContent, rightContent, buttonContainer,
  } = styles;

  return (
    <div className={headerContainer}>
      <Navbar />
      <div className={mainContent}>
        <div className={leftContent}>
          <h1>{t('string.homePage.header.educationalPlatform')}</h1>
          <p>{t('string.homePage.header.chooseCategories')}</p>
          <div className={buttonContainer}>
            <Button
              buttonType="signUp"
              buttonClick={(buttonType) => handleFormVisibility(buttonType)}
            >
              {t('button.homePage.signUp')}
            </Button>
            <Button
              buttonType="signIn"
              buttonClick={(buttonType) => handleFormVisibility(buttonType)}
            >
              {t('button.homePage.signIn')}
            </Button>
          </div>
        </div>
        <div className={rightContent}>
          <img src={EDUCATION_PLATFORM_IMAGE} alt="Education platform logo" />
        </div>
      </div>
    </div>
  );
};
export default HomepageHeader;
