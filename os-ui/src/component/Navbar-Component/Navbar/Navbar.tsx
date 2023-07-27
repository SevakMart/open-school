import { useEffect, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import { Types } from '../../../types/types';
import { openModal } from '../../../redux/Slices/PortalOpenStatus';
import './navbar.scss';
import './navbar_module.scss';
import mobileMenu from '../../../assets/svg/mobileMenu.svg';
import closeIcon from '../../../assets/svg/closeIcon.svg';

const Navbar = () => {
  const dispatch = useDispatch();

  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const mobileMenutype = mobileMenuOpen ? closeIcon : mobileMenu;

  const handleMenuClick = () => {
    setTimeout(() => {
      setMobileMenuOpen((prev) => !prev);
    }, 200);
  };

  const handleClose = () => {
    setTimeout(() => {
      setMobileMenuOpen(false);
    }, 200);
  };

  const mobileMenuRef = useRef<HTMLDivElement | null>(null);
  const closeiconRef = useRef<HTMLImageElement | null>(null);
  const bodyRef = useRef(document.body);

  // when click outside the popup, close it
  const handleClickOutside = (event: MouseEvent) => {
    const target = event.target as Node;
    if (mobileMenuRef.current && !mobileMenuRef.current.contains(target) && target !== closeiconRef.current) {
      handleClose();
    }
  };

  useEffect(() => {
    const listener = (event: MouseEvent) => handleClickOutside(event);
    bodyRef.current.addEventListener('mousedown', listener);
    return () => {
      bodyRef.current.removeEventListener('mousedown', listener);
    };
  }, [mobileMenuRef, closeiconRef]);

  const handleSignIn = () => {
    dispatch(openModal({ buttonType: Types.Button.SIGN_IN }));
  };
  const goToExploreCategoriesPage = () => {
    dispatch(openModal({ buttonType: Types.Button.SIGN_IN }));
  };
  const goToMentorsPage = () => {
    dispatch(openModal({ buttonType: Types.Button.SIGN_IN, isRequestForMentorsPage: true }));
  };

  const { t } = useTranslation();

  return (
    <>
      <div className="desktopMenu">
        <nav className="homePage">
          <h2 className="homePage_Logo">{t('string.homePage.navBar.logo')}</h2>
          <div className="homePage_leftMenu">
            <p className="homePage_leftMenu_item" onClick={goToExploreCategoriesPage}>{t('string.homePage.navBar.exploreCategories')}</p>
            <p className="homePage_leftMenu_item" onClick={goToMentorsPage}>{t('string.homePage.navBar.mentors')}</p>
          </div>
          <div className="homePage_rightMenu">
            <p className="homePage_rightMenu_item">{t('button.homePage.becomeMentor')}</p>
            <button className="homePage_btn" type="button" onClick={handleSignIn}>{t('button.homePage.signIn')}</button>
          </div>
        </nav>
      </div>
      <div className="mobileMenu">
        <h2 className="homePage_Logo">{t('string.homePage.navBar.logo')}</h2>
        <img src={mobileMenutype} alt="menu" ref={closeiconRef} className={`mobileMenuButton ${mobileMenuOpen ? 'open' : ''}`} onClick={handleMenuClick} />
        {mobileMenuOpen && (
          <div className={`mobileList ${mobileMenuOpen ? 'open' : ''}`} ref={mobileMenuRef}>
            <div className="mobileList_info">
              <p className="mobileList_item" onClick={goToExploreCategoriesPage}>
                {t('string.homePage.navBar.exploreCategories')}
              </p>
              <p className="mobileList_item" onClick={goToMentorsPage}>
                {t('string.homePage.navBar.mentors')}
              </p>
              <p className="mobileList_item">{t('button.homePage.becomeMentor')}</p>
              <button className="homePage_btn" type="button" onClick={handleSignIn}>{t('button.homePage.signIn')}</button>
            </div>
          </div>
        )}
      </div>
    </>
  );
};
export default Navbar;
