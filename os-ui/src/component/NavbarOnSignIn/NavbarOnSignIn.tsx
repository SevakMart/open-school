import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import styles from './NavbarOnSignIn.module.scss';
import {
  APP_LOGO, ALL_LEARNING_PATHS, MY_LEARNING_PATHS, MENTORS,
} from '../../constants/Strings';
import BookmarkIcon from '../../icons/Bookmark';
import Notification from '../../icons/Notification';
import DownArrowIcon from '../../icons/DownArrow';
import { removeLoggedInUser } from '../../redux/Slices/loginUserSlice';

const NavbarOnSignIn = () => {
  const dispatch = useDispatch();
  const logout = () => {
    dispatch(removeLoggedInUser());
  };

  const navigate = useNavigate();
  const { mainContent, navMainContent, userInfoContent } = styles;
  return (
    <nav className={mainContent}>
      <h2>{APP_LOGO}</h2>
      <div className={navMainContent}>
        <p>{ALL_LEARNING_PATHS}</p>
        <p>{MY_LEARNING_PATHS}</p>
        <p onClick={() => navigate('/mentors')}>{MENTORS}</p>
        <BookmarkIcon iconSize="1.5rem" isBookmarked={false} />
        <Notification />
        <div className={userInfoContent}>
          <img src="https://reactjs.org/logo-og.png" alt="avatar" />
          <DownArrowIcon />
        </div>
        <p onClick={logout}>Logout</p>
      </div>
    </nav>
  );
};
export default NavbarOnSignIn;
