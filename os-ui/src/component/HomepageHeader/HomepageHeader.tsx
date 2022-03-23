import { useState } from 'react';
import Navbar from '../Navbar/Navbar';
import styles from './HomepageHeader.module.scss';
import {
  FREE_EDUCATIONAL_PLATFORM, SIGN_UP, HEADER_INTRODUCTION, SIGN_IN, EDUCATION_PLATFORM_IMAGE,
} from '../../constants/Strings';
import Button from '../Button/Button';
import Form from '../Forms/SignUpSignInForm';

const HomepageHeader = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [clickedButtonType, setClickedButtonType] = useState('');
  const {
    headerContainer, mainContent, leftContent, rightContent, buttonContainer,
  } = styles;
  const handleButtonClick = (buttonType:string) => {
    if (buttonType === 'signUp') {
      setIsOpen(true);
      setClickedButtonType(buttonType);
    } else if (buttonType === 'signIn') {
      setIsOpen(true);
      setClickedButtonType(buttonType);
    }
  };

  return (
    <div className={headerContainer}>
      <Navbar />
      <div className={mainContent}>
        <div className={leftContent}>
          <h1>{FREE_EDUCATIONAL_PLATFORM}</h1>
          <p>{HEADER_INTRODUCTION}</p>
          <div className={buttonContainer}>
            <Button
              buttonType="signUp"
              buttonClick={handleButtonClick}
            >
              {SIGN_UP}
            </Button>
            <Button
              buttonType="signIn"
              buttonClick={handleButtonClick}
            >
              {SIGN_IN}
            </Button>
          </div>
        </div>
        <div className={rightContent}>
          <img src={EDUCATION_PLATFORM_IMAGE} alt="Education platform logo" />
        </div>
      </div>
      {
        isOpen && clickedButtonType === 'signUp'
          ? <Form formType={clickedButtonType} /> : null
      }
    </div>
  );
};
export default HomepageHeader;
