import {
  APP_LOGO, EXPLORE_CATEGORIES, MENTORS, BECOME_A_MENTOR, SIGN_IN,
} from '../../constants/Strings';
import styles from './Navbar.module.scss';

const Navbar = () => {
  const {
    navContainer, navMainContent, categoriesMentors, buttonContent,
  } = styles;
  return (
    <nav className={navContainer}>
      <h2>{APP_LOGO}</h2>
      <div className={navMainContent}>
        <div className={categoriesMentors}>
          <p>{EXPLORE_CATEGORIES}</p>
          <p>{MENTORS}</p>
        </div>
        <div className={buttonContent}>
          <button type="button">{BECOME_A_MENTOR}</button>
          <button type="button">{SIGN_IN}</button>
        </div>
      </div>
    </nav>
  );
};
export default Navbar;
