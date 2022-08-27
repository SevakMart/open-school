import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import styles from './SignUp.module.scss';
import CloseIcon from '../../../icons/Close';
import LinkedinIcon1 from '../../../icons/Linkedin1';
import EmailIcon1 from '../../../icons/Email1';
import Header from '../Header/Header';
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
    <>
      {isSignUp ? <h3 data-testid="successfulSignUpMessage">{succesfullSignUpMessage}</h3>
        : (
          <>
            <Header mainTitle={t('string.signUp.title')} shouldRemoveIconContent={false} />
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
    </>
  );
};
export default SignUp;
