import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import HomepageHeader from '../../component/HomepageHeader/HomepageHeader';
import Footer from '../../component/Footer/Footer';
import HomepageCategories from './Subcomponents/Categories/Categories';
import HomepageMentors from './Subcomponents/Mentors/Mentors';
import SignUp from '../../component/SignUpSignIn/SignUp/SignUp';
import SignIn from '../../component/SignUpSignIn/SignIn/SignIn';
import ForgotPassword from '../../component/SignUpSignIn/SignIn/ForgotPassword/ForgotPassword';
import SuccessMessage from '../../component/SignUpSignIn/Success-Message/Success-Message';
import ResetPassword from '../../component/SignUpSignIn/SignIn/ResetPassword/ResetPassword';
import Verification from '../../component/SignUpSignIn/Verification/Verification';
import { Portal } from '../../component/Portal/Portal';
import { Types } from '../../types/types';
import { PortalStatus } from '../../types/PortalStatusType';

/* eslint-disable max-len */

const Homepage = () => {
  const portalStatus = useSelector<RootState>((state) => state.portalStatus);
  const {
    isOpen, buttonType, withSuccessMessage, isSignUpSuccessfulRegistration, isResetPasswordSuccessfulMessage, isResendVerificationEmailMessage,
  } = portalStatus as PortalStatus;

  return (
    <>
      <HomepageHeader />
      <HomepageCategories />
      <HomepageMentors />
      <Footer />
      <Portal.FormPortal isOpen={isOpen}>
        {/* eslint-disable max-len */}
        {isOpen && buttonType === Types.Button.SIGN_UP && <SignUp />}
        {isOpen && buttonType === Types.Button.VERIFY && <Verification />}
        {isOpen && buttonType === Types.Button.SIGN_IN && <SignIn />}
        {isOpen && buttonType === Types.Button.RESET_PASSWORD && <ResetPassword />}
        {isOpen && buttonType === Types.Button.FORGOT_PASSWORD && <ForgotPassword />}
        {isOpen && buttonType === Types.Button.SUCCESS_MESSAGE && <SuccessMessage message={withSuccessMessage} isSignUpSuccessfulRegistration={isSignUpSuccessfulRegistration} isResetPasswordSuccessfulMessage={isResetPasswordSuccessfulMessage} isResendVerificationEmailMessage={isResendVerificationEmailMessage} />}
      </Portal.FormPortal>
    </>
  );
};
export default Homepage;
