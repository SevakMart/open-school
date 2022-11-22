import { useMemo } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import HomepageHeader from './Subcomponents/Header/Header';
import Footer from '../../component/Footer/Footer';
import HomepageCategories from './Subcomponents/Categories/Categories';
import HomepageMentors from './Subcomponents/Mentors/Mentors';
import SignUp from '../../component/SignUpSignIn/SignUp/SignUp';
import SignIn from '../../component/SignUpSignIn/SignIn/SignIn';
import ForgotPassword from '../../component/SignUpSignIn/SignIn/ForgotPassword/ForgotPassword';
import SuccessMessage from '../../component/SignUpSignIn/Success-Message/Success-Message';
import ResetPassword from '../../component/SignUpSignIn/SignIn/ResetPassword/ResetPassword';
import Verification from '../../component/SignUpSignIn/Verification/Verification';
import { userContext } from '../../contexts/Contexts';
import { Portal } from '../../component/Portal/Portal';
import { Types } from '../../types/types';
import { PortalStatus } from '../../types/PortalStatusType';

const Homepage = ({ userInfo }:{userInfo:any}) => {
  const portalStatus = useSelector<RootState>((state) => state.portalStatus);

  const {
    isOpen, buttonType, withSuccessMessage, isSignUpSuccessfulRegistration,
    isResetPasswordSuccessfulMessage, isResendVerificationEmailMessage,
  } = portalStatus as PortalStatus;
  const idAndToken = useMemo(() => ({
    token: userInfo ? (userInfo as any).token : '',
    id: userInfo ? (userInfo as any).id : -1,
  }), [{ ...userInfo }]);

  return (
    <>
      <userContext.Provider value={idAndToken}>
        <HomepageHeader />
        <HomepageCategories />
        <HomepageMentors />
      </userContext.Provider>
      <Footer />
      <Portal.FormPortal isOpen={isOpen}>
        {isOpen && buttonType === Types.Button.SIGN_UP && <SignUp />}
        {isOpen && buttonType === Types.Button.VERIFY && <Verification />}
        {isOpen && buttonType === Types.Button.SIGN_IN && <SignIn />}
        {isOpen && buttonType === Types.Button.RESET_PASSWORD && <ResetPassword />}
        {isOpen && buttonType === Types.Button.FORGOT_PASSWORD && <ForgotPassword />}
        {isOpen && buttonType === Types.Button.SUCCESS_MESSAGE && (
        <SuccessMessage
          message={withSuccessMessage}
          isSignUpSuccessfulRegistration={isSignUpSuccessfulRegistration}
          isResetPasswordSuccessfulMessage={isResetPasswordSuccessfulMessage}
          isResendVerificationEmailMessage={isResendVerificationEmailMessage}
        />
        )}
      </Portal.FormPortal>
    </>
  );
};
export default Homepage;
