import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { RootState } from '../../redux/Store';
import HomepageHeader from '../../component/HomepageHeader/HomepageHeader';
import Footer from '../../component/Footer/Footer';
import Button from '../../component/Button/Button';
import styles from './Homepage.module.scss';
import { removeLoggedInUser } from '../../redux/Slices/loginUserSlice';
import categoriesService from '../../services/categoriesService';
import HomepageCategories from './Subcomponents/Categories/Categories';
import HomepageMentors from './Subcomponents/Mentors/Mentors';
import SignUp from '../../component/SignUp/SignUp';
import SignIn from '../../component/SignIn/SignIn';

const Homepage = () => {
  const dispatch = useDispatch();
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isOpen, setIsOpen] = useState(false);
  const [clickedButtonType, setClickedButtonType] = useState('');
  const { mainContainer, buttonContainer } = styles;
  const { t, i18n } = useTranslation('common');
  const handleButtonClick = (buttonType:string) => {
    switch (buttonType) {
      case 'signUp':
        setIsOpen(true);
        setClickedButtonType(buttonType);
        break;
      case 'signIn':
        setIsOpen(true);
        setClickedButtonType(buttonType);
        break;
      case 'closeButton':
        setIsOpen(false);
        break;
    }
  };

  useEffect(() => {
    let cancel = false;
    if ((userInfo as any).token) {
      categoriesService.getCategories({ page: 0, size: 6 }, (userInfo as any).token).then((res) => {
        const { status } = res;
        if (cancel) return;
        if (status === 401) {
          dispatch(removeLoggedInUser());
          setIsLoggedIn(false);
        } else setIsLoggedIn(true);
      });
    }
    // The return is to prevent memory leackage
    return () => { cancel = true; };
  }, []);

  return (
    <>
      <HomepageHeader
        handleFormVisibility={handleButtonClick}
      />
      <HomepageCategories isLoggedIn={isLoggedIn} />
      <HomepageMentors isLoggedIn={isLoggedIn} handleButtonClick={handleButtonClick} />
      <div className={mainContainer}>
        <h2>Start Your Journey Now!</h2>
        <div className={buttonContainer}>
          <Button buttonType="signUp" buttonClick={handleButtonClick}>Sign up as a Student</Button>
          <Button buttonType="signUp" buttonClick={handleButtonClick}>sign up as a mentor</Button>
        </div>
      </div>
      <Footer />
      {isOpen && clickedButtonType === 'signUp'
        ? <SignUp handleSignUpClicks={handleButtonClick} />
        : isOpen && clickedButtonType === 'signIn'
          ? <SignIn handleSignInClicks={handleButtonClick} />
          : null}
    </>
  );
};
export default Homepage;
