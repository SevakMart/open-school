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
    switch (buttonType) {
      case 'signUp':
        setIsOpen(true);
        setClickedButtonType(buttonType);
        break;
      case 'signIn':
        setIsOpen(true);
        setClickedButtonType(buttonType);
        break;
      case 'closeButton':
        setIsOpen(false);
        break;
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
        isOpen && (clickedButtonType === 'signUp' || clickedButtonType === 'signIn')
          ? (
            <Form
              formType={clickedButtonType}
              formClick={handleButtonClick}
            />
          ) : null
      }
    </div>
  );
};
export default HomepageHeader;
