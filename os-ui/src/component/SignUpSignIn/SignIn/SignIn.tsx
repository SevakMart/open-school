import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import SignInForm from './SignInForm';
import styles from './SignIn.module.scss';
import CloseIcon from '../../../icons/Close';
import LinkedinIcon1 from '../../../icons/Linkedin1';
import EmailIcon1 from '../../../icons/Email1';
import ForgotPassword from './ForgotPassword/ForgotPassword';

const SignIn = ({ handleSignInClicks }:{handleSignInClicks(arg:string):void}) => {
  const { t } = useTranslation();
  const {
    mainContainer, formContainer, headerContent, iconContent, alreadyHaveAccount,
  } = styles;
  const navigate = useNavigate();
  const [isSignedIn, setIsSignedIn] = useState(false);
  const [forgotPasswordIsSet, setForgotPasswordIsSet] = useState(false);

  const handleSignIn = () => {
    setIsSignedIn(true);
  };

  const forgotPassword = () => {
    setForgotPasswordIsSet(true);
  };

  const returnToSignInForm = () => {
    setForgotPasswordIsSet(false);
    setIsSignedIn(false);
  };

  useEffect(() => {
    if (isSignedIn) {
      navigate('/categories/subcategories');
    }
  }, [isSignedIn]);

  return (
    <div className={mainContainer}>
      <div className={formContainer}>
        { forgotPasswordIsSet ? <ForgotPassword returnToSignInForm={returnToSignInForm} />
          : (
            <>
              <CloseIcon />
              <div className={headerContent}>
                <h2>{t('string.signIn.title')}</h2>
                <div className={iconContent}>
                  <button type="button"><LinkedinIcon1 /></button>
                  <button type="button"><EmailIcon1 /></button>
                </div>
                <p>{t('string.signIn.or')}</p>
              </div>
              <SignInForm
                signInForm="default"
                handleSignIn={handleSignIn}
                forgotPasswordHandler={forgotPassword}
              />
              <p className={alreadyHaveAccount}>
                {t('string.signIn.dontHaveAccount')}
                <span><button type="button" onClick={() => handleSignInClicks('signUp')}>{t('button.homePage.signUp')}</button></span>
              </p>
            </>
          )}
      </div>
    </div>
  );
};
export default SignIn;
