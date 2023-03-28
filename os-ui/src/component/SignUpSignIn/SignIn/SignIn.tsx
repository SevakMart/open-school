import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { RootState } from '../../../redux/Store';
import SignInForm from './SignInForm';
import Header from '../Header/Header';
import Footer from '../Footer/Footer';
import { PortalStatus } from '../../../types/PortalStatusType';
import { Types } from '../../../types/types';

/* eslint-disable max-len */

const SignIn = () => {
  const portalState = useSelector<RootState>((state) => state.portalStatus);
  const { isRequestForMentorsPage } = portalState as PortalStatus;
  const [isSignedIn, setIsSignedIn] = useState(false);
  const navigate = useNavigate();
  const { t } = useTranslation();

  useEffect(() => {
    if (isSignedIn) {
      if (isRequestForMentorsPage) {
        navigate('/mentors');
      } else navigate('/myLearningPath');
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
      <SignInForm signInForm="default" handleSignIn={() => setIsSignedIn(true)} />
      <Footer
        mainText={t('string.signIn.dontHaveAccount')}
        buttonType={Types.Button.SIGN_UP}
        buttonText={t('button.homePage.signUp')}
      />
    </>
  );
};
export default SignIn;
