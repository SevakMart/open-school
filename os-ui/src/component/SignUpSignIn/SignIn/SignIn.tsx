import { useTranslation } from 'react-i18next';
import SignInForm from './SignInForm';
import Header from '../Header/Header';
import Footer from '../Footer/Footer';
import { Types } from '../../../types/types';

const SignIn = () => {
  const { t } = useTranslation();

  return (
    <>
      <Header
        mainTitle={t('string.signIn.title')}
        shouldRemoveIconContent={false}
        isForgotPasswordContent={false}
        isVerificationContent={false}
      />
      <SignInForm signInForm="default" />
      <Footer
        mainText={t('string.signIn.dontHaveAccount')}
        buttonType={Types.Button.SIGN_UP}
        buttonText={t('button.homePage.signUp')}
      />
    </>
  );
};
export default SignIn;
