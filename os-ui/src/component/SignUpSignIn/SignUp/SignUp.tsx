import { useTranslation } from 'react-i18next';
import Header from '../Header/Header';
import Footer from '../Footer/Footer';
import SignUpRegistrationForm from './RegistrationForm';
import { Types } from '../../../types/types';

const SignUp = () => {
  const { t } = useTranslation();
  return (
    <>
      <Header
        mainTitle={t('string.signUp.title')}
        shouldRemoveIconContent={false}
        isForgotPasswordContent={false}
        isVerificationContent={false}
      />
      <SignUpRegistrationForm
        registrationForm="default"
      />
      <Footer
        mainText={t('string.signUp.haveAccount')}
        buttonType={Types.Button.SIGN_IN}
        buttonText={t('button.homePage.signIn')}
      />

    </>
  );
};
export default SignUp;
