import { useState, useEffect, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate, useLocation } from 'react-router-dom';
import styles from './NavbarOnSignIn.module.scss';
import ArrowDownIcon from '../../../assets/svg/Arrow-down.svg';
import NotificationIcon from '../../../assets/svg/Notification.svg';
import BookmarkIcon from '../../../icons/Bookmark';
import { SignoutIcon } from '../../../icons/Signout/Signout';
import ProfilePortalContent from '../../ProfilePortalContent/ProfilePortalContent';
import { Portal } from '../../Portal/Portal';

const NavbarOnSignIn = ({ currentUserEnrolled }: { currentUserEnrolled: boolean }) => {
  const [isOpen, setIsOpen] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();
  const arrowRef = useRef(null);
  const { t } = useTranslation();

  const {
    mainContent, navMainContent, userInfoContent,
    bookmarkIconStyle, notificationIcon, avatarLogo, arrowDownIcon, mainConentDecrptionPage, navbarTitle, decrptionPageTitle,
  } = styles;

  const handleProfilePortal = () => {
    setIsOpen((prevState) => !prevState);
  };

  const popupRef = useRef<HTMLDivElement | null>(null);
  const bodyRef = useRef(document.body);

  const handleClickOutside = (event: MouseEvent) => {
    const target = event.target as Node;
    if (!popupRef?.current?.contains(target) && target !== arrowRef.current) {
      setIsOpen(false);
    }
  };

  useEffect(() => {
    const listener = (event: MouseEvent) => handleClickOutside(event);
    bodyRef.current.addEventListener('mousedown', listener);
    return () => {
      bodyRef.current.removeEventListener('mousedown', listener);
    };
  }, []);

  const isCourseDescriptionPage = location.pathname?.match(/^\/userCourse\/\d+$/);

  return (
    <nav className={!currentUserEnrolled && isCourseDescriptionPage ? mainConentDecrptionPage : mainContent}>
      <h2 onClick={() => navigate('/homepage/WhenSignin')}>{t('string.homePage.navBar.logo')}</h2>
      <div className={navMainContent}>
        <p className={!currentUserEnrolled && isCourseDescriptionPage ? decrptionPageTitle : navbarTitle} onClick={() => navigate('/exploreLearningPaths')}>{t('string.homePage.navBar.allLearningPaths')}</p>
        <p className={!currentUserEnrolled && isCourseDescriptionPage ? decrptionPageTitle : navbarTitle} onClick={() => navigate('/myLearningPath')}>{t('string.homePage.navBar.myLearningPaths')}</p>
        <p className={!currentUserEnrolled && isCourseDescriptionPage ? decrptionPageTitle : navbarTitle} onClick={() => navigate('/mentors')}>{t('string.homePage.navBar.mentors')}</p>
        <div className={bookmarkIconStyle}>
          <BookmarkIcon iconSize="20px" />
        </div>
        <img className={notificationIcon} src={NotificationIcon} alt="Notification" />
        <div className={userInfoContent}>
          <img className={avatarLogo} src="https://reactjs.org/logo-og.png" alt="avatar" />
          <img ref={arrowRef} className={arrowDownIcon} style={isOpen ? { transform: 'rotate(180deg)' } : undefined} src={ArrowDownIcon} alt="Arrow down Icon" onClick={handleProfilePortal} />
        </div>
        <div ref={popupRef}>
          <Portal.ProfilePortal isOpen={isOpen}>
            <div ref={popupRef}>
              <ProfilePortalContent icon={<SignoutIcon />} isSignOut>
                {t('string.profilePortal.signOut')}
              </ProfilePortalContent>
            </div>
          </Portal.ProfilePortal>
        </div>
      </div>
    </nav>
  );
};

export default NavbarOnSignIn;
