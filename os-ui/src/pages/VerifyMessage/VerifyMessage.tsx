import { useState } from 'react';
import SignInForm from '../../component/SignIn/SignInForm';
import styles from '../../component/SignIn/SignIn.module.scss';
import CloseIcon from '../../icons/Close';
import LinkedinIcon1 from '../../icons/Linkedin1';
import EmailIcon1 from '../../icons/Email1';
import ForgotPassword from '../../component/SignIn/ForgotPassword/ForgotPassword';
import { SIGN_UP } from '../../constants/Strings';
import Message from './Message/Message';

const VerifyMessage = ({ handleSignInClicks }:{handleSignInClicks(arg:string):void}) => {
  const {
    mainContainer, formContainer, headerContent, iconContent, alreadyHaveAccount,
  } = styles;

  const [isSignedIn, setIsSignedIn] = useState(false);
  const [forgotPasswordIsSet, setForgotPasswordIsSet] = useState(false);
  const [succesfulSignInMessage, setSuccessfulSignInMessage] = useState('');
  const [isVerifyForLogin, setIsVerifyForLogin] = useState(false);

  const handleSignIn = (message:string) => {
    setIsSignedIn(true);
    setSuccessfulSignInMessage(message);
  };

  const forgotPassword = () => {
    setForgotPasswordIsSet(true);
  };

  const returnToSignInForm = () => {
    setForgotPasswordIsSet(false);
    setIsSignedIn(false);
  };

  const reSend = () => {
    // TO DO
  };

  const goToLogin = () => {
    setIsVerifyForLogin(true);
  };

  return (
    <div className={mainContainer}>
      <div className={formContainer}>
        { !isVerifyForLogin && <Message isVerify={isVerifyForLogin} reSend={reSend} goToLogin={goToLogin} />}
        {isVerifyForLogin && (
        <>
          { forgotPasswordIsSet ? <ForgotPassword returnToSignInForm={returnToSignInForm} />
            : isSignedIn ? <h3 data-testid="successfulSignInMessage">{succesfulSignInMessage}</h3>
              : (
                <>
                  <CloseIcon handleClosing={() => handleSignInClicks('closeButton')} />
                  <div className={headerContent}>
                    <h2>Sign In!</h2>
                    <div className={iconContent}>
                      <button type="button"><LinkedinIcon1 /></button>
                      <button type="button"><EmailIcon1 /></button>
                    </div>
                    <p>Or</p>
                  </div>
                  <SignInForm
                    signInForm="default"
                    handleSignIn={handleSignIn}
                    forgotPasswordHandler={forgotPassword}
                  />
                  <p className={alreadyHaveAccount}>
                    {'Don\'t Have An Account?'}
                    <span><button type="button" onClick={() => handleSignInClicks('signUp')}>{SIGN_UP}</button></span>
                  </p>
                </>
              )}
        </>
        )}
      </div>
    </div>
  );
};
export default VerifyMessage;
