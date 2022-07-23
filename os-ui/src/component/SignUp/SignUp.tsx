import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import styles from './SignUp.module.scss';
import CloseIcon from '../../icons/Close';
import LinkedinIcon1 from '../../icons/Linkedin1';
import EmailIcon1 from '../../icons/Email1';
import SignUpRegistrationForm from './RegistrationForm';

const SignUp = ({ handleSignUpClicks }:{handleSignUpClicks(arg:string):void}) => {
  const { t } = useTranslation();
  const {
    mainContainer, formContainer, headerContent, iconContent, alreadyHaveAccount,
  } = styles;
  const [isSignUp, setIsSignUp] = useState(false);
  const [succesfullSignUpMessage, setSuccessfulSignUpMessage] = useState('');

  const handleSignUp = (message:string) => {
    setIsSignUp(true);
    setSuccessfulSignUpMessage(message);
  };

  useEffect(() => {
    let timer:any;
    if (isSignUp) {
      timer = setTimeout(() => handleSignUpClicks('verify'), 3000);
    }
    return () => clearTimeout(timer);
  }, [isSignUp]);
  return (
    <div className={mainContainer}>
      <div className={formContainer}>
        {isSignUp ? <h3 data-testid="successfulSignUpMessage">{succesfullSignUpMessage}</h3>
          : (
            <>
              <CloseIcon handleClosing={() => handleSignUpClicks('closeButton')} />
              <div className={headerContent}>
                <h2>{t('string.signUp.title')}</h2>
                <div className={iconContent}>
                  <button type="button"><LinkedinIcon1 /></button>
                  <button type="button"><EmailIcon1 /></button>
                </div>
                <p>{t('string.signIn.or')}</p>
              </div>
              <SignUpRegistrationForm
                registrationForm="default"
                switchToSignInForm={handleSignUp}
              />
              <p className={alreadyHaveAccount}>
                {t('string.signUp.haveAccount')}
                <span><button type="button" onClick={() => handleSignUpClicks('verify')}>{t('button.homePage.signIn')}</button></span>
              </p>
            </>
          )}
      </div>
    </div>
  );
};
export default SignUp;
