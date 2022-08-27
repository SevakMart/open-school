import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import HomepageHeader from '../../component/HomepageHeader/HomepageHeader';
import Footer from '../../component/Footer/Footer';
import HomepageCategories from './Subcomponents/Categories/Categories';
import HomepageMentors from './Subcomponents/Mentors/Mentors';
import SignUp from '../../component/SignUpSignIn/SignUp/SignUp';
import SignIn from '../../component/SignUpSignIn/SignIn/SignIn';
import VerifyMessage from '../VerifyMessage/VerifyMessage';
import Portal from '../../component/Portal/Portal';
import { Types } from '../../types/types';
import { PortalStatus } from '../../types/PortalStatusType';

const Homepage = () => {
  const portalStatus = useSelector<RootState>((state) => state.portalStatus);
  const { isOpen, buttonType } = portalStatus as PortalStatus;

  return (
    <>
      <HomepageHeader />
      <HomepageCategories />
      <HomepageMentors />
      <Footer />
      <Portal isOpen={isOpen}>
        {/* eslint-disable max-len */}
        {isOpen && buttonType === Types.Button.SIGN_UP && <SignUp handleSignUpClicks={() => null} />}
        {isOpen && buttonType === Types.Button.VERIFY && <VerifyMessage handleSignInClicks={() => null} />}
        {isOpen && buttonType === Types.Button.SIGN_IN && <SignIn handleSignInClicks={() => null} />}
      </Portal>
    </>
  );
};
export default Homepage;
