import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './SignIn.module.scss';
import CloseIcon from '../../icons/Close';
import LinkedinIcon1 from '../../icons/Linkedin1';
import EmailIcon1 from '../../icons/Email1';
import { SIGN_UP } from '../../constants/Strings';
import Form from '../Forms/SignUpSignInForm';

const SignIn = ({ handleSignInClicks }:{handleSignInClicks(arg:string):void}) => {
  const {
    mainContainer, formContainer, headerContent, iconContent, alreadyHaveAccount, inputContent,
  } = styles;
  const navigate = useNavigate();
  const [isSignedIn, setIsSignedIn] = useState(false);
  const [succesfulSignInMessage, setSuccessfulSignInMessage] = useState('');

  const handleSignIn = (message:string) => {
    setIsSignedIn(true);
    setSuccessfulSignInMessage(message);
  };
  useEffect(() => {
    let timer:any;
    if (isSignedIn) {
      timer = setTimeout(() => navigate('/choose_categories'), 3000);
    }
    return () => clearTimeout(timer);
  }, [isSignedIn]);

  return (
    <div className={mainContainer}>
      <div className={formContainer}>
        { isSignedIn ? <h3>{succesfulSignInMessage}</h3>
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
              <form className={inputContent}>
                <Form
                  formType="signIn"
                  handleSignIn={handleSignIn}
                />
              </form>
              <p className={alreadyHaveAccount}>
                {'Don\'t Have An Account?'}
                <span><button type="button" onClick={() => handleSignInClicks('signUp')}>{SIGN_UP}</button></span>
              </p>
            </>
          )}
      </div>
    </div>
  );
};
export default SignIn;
