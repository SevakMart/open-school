import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { RootState } from '../../redux/Store';
import SignInForm from './SignInForm';
import styles from './SignIn.module.scss';
import CloseIcon from '../../icons/Close';
import LinkedinIcon1 from '../../icons/Linkedin1';
import EmailIcon1 from '../../icons/Email1';
import { SIGN_UP } from '../../constants/Strings';

const SignIn = ({ handleSignInClicks }:{handleSignInClicks(arg:string):void}) => {
  const {
    mainContainer, formContainer, headerContent, iconContent, alreadyHaveAccount,
  } = styles;
  const userInfo = useSelector<RootState>((state) => state.userInfo);
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
      timer = setTimeout(() => navigate(`/categories/subcategories/userId=${(userInfo as any).id}`), 3000);
    }
    return () => clearTimeout(timer);
  }, [isSignedIn]);

  return (
    <div className={mainContainer}>
      <div className={formContainer}>
        { isSignedIn ? <h3 data-testid="successfulSignInMessage">{succesfulSignInMessage}</h3>
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
              />
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
