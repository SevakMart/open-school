import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import styles from './NavbarOnSignIn.module.scss';
import ArrowDownIcon from '../../../assets/svg/Arrow-down.svg';
import NotificationIcon from '../../../assets/svg/Notification.svg';
import BookmarkIcon from '../../../icons/Bookmark';
import { storage } from '../../../services/storage/storage';

/* eslint-disable max-len */

const NavbarOnSignIn = () => {
  const navigate = useNavigate();
  const { t } = useTranslation();
  const {
    mainContent, navMainContent, userInfoContent, bookmarkIconStyle, notificationIcon, avatarLogo, arrowDownIcon,
  } = styles;

  const handleLogout = () => {
    storage.removeItemFromLocalStorage('userInfo');
    navigate('/');
  };

  return (
    <nav className={mainContent}>
      <h2>{t('string.homePage.navBar.logo')}</h2>
      <div className={navMainContent}>
        <p onClick={() => navigate('/mentors')}>{t('string.homePage.navBar.allLearningPaths')}</p>
        <p onClick={() => navigate('/myLearningPath')}>{t('string.homePage.navBar.myLearningPaths')}</p>
        <p onClick={() => navigate('/exploreLearningPaths')}>{t('string.homePage.navBar.mentors')}</p>
        <div className={bookmarkIconStyle}>
          <BookmarkIcon iconSize="20px" isBookmarked={false} />
        </div>
        <img className={notificationIcon} src={NotificationIcon} alt="Notification" />
        <div className={userInfoContent}>
          <img className={avatarLogo} src="https://reactjs.org/logo-og.png" alt="avatar" />
          <img className={arrowDownIcon} src={ArrowDownIcon} alt="Arrow down Icon" />
        </div>
        <p onClick={handleLogout}>Logout</p>
      </div>
    </nav>
  );
};
export default NavbarOnSignIn;
