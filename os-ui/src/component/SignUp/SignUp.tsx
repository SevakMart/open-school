import { useState, useEffect } from 'react';
import styles from './SignUp.module.scss';
import CloseIcon from '../../icons/Close';
import LinkedinIcon1 from '../../icons/Linkedin1';
import EmailIcon1 from '../../icons/Email1';
import { SIGN_IN } from '../../constants/Strings';
import Form from '../Forms/SignUpSignInForm';

const SignUp = ({ handleSignUpClicks }:{handleSignUpClicks(arg:string):void}) => {
  const {
    mainContainer, formContainer, headerContent, iconContent, alreadyHaveAccount, inputContent,
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
      timer = setTimeout(() => handleSignUpClicks('signIn'), 3000);
    }
    return () => clearTimeout(timer);
  }, [isSignUp]);
  return (
    <div className={mainContainer}>
      <div className={formContainer}>
        {isSignUp ? <h3>{succesfullSignUpMessage}</h3>
          : (
            <>
              <CloseIcon handleClosing={() => handleSignUpClicks('closeButton')} />
              <div className={headerContent}>
                <h2>Sign Up!</h2>
                <div className={iconContent}>
                  <button type="button"><LinkedinIcon1 /></button>
                  <button type="button"><EmailIcon1 /></button>
                </div>
                <p>Or</p>
              </div>
              <form className={inputContent}>
                <Form
                  formType="signUp"
                  switchToSignInForm={handleSignUp}
                />
              </form>
              <p className={alreadyHaveAccount}>
                Have An Account?
                <span><button type="button" onClick={() => handleSignUpClicks('signIn')}>{SIGN_IN}</button></span>
              </p>
            </>
          )}
      </div>
    </div>
  );
};
export default SignUp;
