import { useState } from 'react';
import HomepageHeader from '../../component/HomepageHeader/HomepageHeader';
import Footer from '../../component/Footer/Footer';
import HomepageCategories from './Subcomponents/Categories/Categories';
import HomepageMentors from './Subcomponents/Mentors/Mentors';
import SignUp from '../../component/SignUp/SignUp';
import SignIn from '../../component/SignIn/SignIn';
import VerifyMessage from '../VerifyMessage/VerifyMessage';
import { Types } from '../../types/types';

const Homepage = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [clickedButtonType, setClickedButtonType] = useState('');

  const manipulateByButtonType = (buttonType: string) => {
    setIsOpen(true);
    setClickedButtonType(buttonType);
  };

  const handleButtonClick = (buttonType:string) => {
    const isTrue = (buttonType === Types.Button.SIGN_IN
      || Types.Button.SIGN_UP || Types.Button.VERIFY);
    if (isTrue) {
      manipulateByButtonType(buttonType);
    } else {
      setIsOpen(false);
    }
  };

  return (
    <>
      <HomepageHeader
        handleFormVisibility={handleButtonClick}
      />
      <HomepageCategories />
      <HomepageMentors handleButtonClick={handleButtonClick} />
      <Footer />
      {isOpen && clickedButtonType === 'signUp' ? <SignUp handleSignUpClicks={handleButtonClick} />
        : isOpen && clickedButtonType === 'verify' ? <VerifyMessage handleSignInClicks={handleButtonClick} />
          : isOpen && clickedButtonType === 'signIn' ? <SignIn handleSignInClicks={handleButtonClick} />
            : null}
    </>
  );
};
export default Homepage;
