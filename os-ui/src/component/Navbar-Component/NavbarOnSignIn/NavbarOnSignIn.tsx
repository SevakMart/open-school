import { useContext, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router-dom';
import styles from './NavbarOnSignIn.module.scss';
import ArrowDownIcon from '../../../assets/svg/Arrow-down.svg';
import NotificationIcon from '../../../assets/svg/Notification.svg';
import BookmarkIcon from '../../../icons/Bookmark';
import { SignoutIcon } from '../../../icons/Signout/Signout';
import ProfilePortalContent from '../../ProfilePortalContent/ProfilePortalContent';
import { Portal } from '../../Portal/Portal';
import { signInContext } from '../../../contexts/Contexts';

/* eslint-disable max-len */

const NavbarOnSignIn = () => {
  const [isOpen, setIsOpen] = useState(false);
  const navigate = useNavigate();
  const { t } = useTranslation();
  // const { signIn, setSignIn } = useContext(signInContext);
  const {
    mainContent, navMainContent, userInfoContent, bookmarkIconStyle, notificationIcon, avatarLogo, arrowDownIcon,
  } = styles;

  const handleProfilePortal = () => {
    setIsOpen((prevState) => !prevState);
  };

  return (
    <nav className={mainContent}>
      <Link to="/">
        <h2>{t('string.homePage.navBar.logo')}</h2>
      </Link>
      {/* <h2 onClick={() => { navigate('/'); setSignIn(true); }}>{t('string.homePage.navBar.logo')}</h2> */}
      {/* {console.log(`SIGN IN in Navbar--->  ${signIn}`)} */}
      {/* {console.log(`User Info in Navbar--->  ${userIF}`)} */}
      <div className={navMainContent}>
        <p onClick={() => navigate('/exploreLearningPaths')}>{t('string.homePage.navBar.allLearningPaths')}</p>
        <p onClick={() => navigate('/myLearningPath')}>{t('string.homePage.navBar.myLearningPaths')}</p>
        <p onClick={() => navigate('/mentors')}>{t('string.homePage.navBar.mentors')}</p>
        <div className={bookmarkIconStyle}>
          <BookmarkIcon iconSize="20px" />
        </div>
        <img className={notificationIcon} src={NotificationIcon} alt="Notification" />
        <div className={userInfoContent}>
          <img className={avatarLogo} src="https://reactjs.org/logo-og.png" alt="avatar" />
          <img className={arrowDownIcon} style={isOpen ? { transform: 'rotate(180deg)' } : undefined} src={ArrowDownIcon} alt="Arrow down Icon" onClick={handleProfilePortal} />
        </div>
        {/* {console.log(`2 IS OPEN IN MAIN--->==== ${isOpen}`)} */}
        <Portal.ProfilePortal isOpen={isOpen}>
          <>
            <ProfilePortalContent icon={<SignoutIcon />} isSignOut>
              {t('string.profilePortal.signOut')}
            </ProfilePortalContent>
            <ProfilePortalContent icon={<SignoutIcon />}>
              {t('string.profilePortal.signOut')}
            </ProfilePortalContent>
          </>
        </Portal.ProfilePortal>
      </div>
    </nav>
  );
};
export default NavbarOnSignIn;
