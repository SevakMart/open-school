import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import SignInForm from './SignInForm';
import Header from '../Header/Header';
import Footer from '../Footer/Footer';
import { Types } from '../../../types/types';

const SignIn = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [isSignedIn, setIsSignedIn] = useState(false);
  const [forgotPasswordIsSet, setForgotPasswordIsSet] = useState(false);

  const handleSignIn = () => {
    setIsSignedIn(true);
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
    <>
      <Header
        mainTitle={t('string.signIn.title')}
        shouldRemoveIconContent={false}
        isForgotPasswordContent={false}
        isVerificationContent={false}
      />
      <SignInForm
        signInForm="default"
        handleSignIn={handleSignIn}
      />
      <Footer
        mainText={t('string.signIn.dontHaveAccount')}
        buttonType={Types.Button.SIGN_UP}
        buttonText={t('button.homePage.signUp')}
      />
    </>
  );
};
export default SignIn;
