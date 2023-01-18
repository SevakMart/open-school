import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { useContext } from 'react';
import { removeUserInfoFromLocalStorage } from '../../redux/Slices/UserInfoSlice';
import styles from './ProfilePortalContent.module.scss';
import { signInContext } from '../../contexts/Contexts';

const ProfilePortalContent = ({ icon, children, isSignOut }:any) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { setSignIn } = useContext(signInContext);
  const { mainContent, iconContainer, titleContent } = styles;

  const handleClick = () => {
    if (isSignOut) {
      dispatch(removeUserInfoFromLocalStorage());
      navigate('/');
      setSignIn(false);
    }
  };

  return (
    <div className={mainContent} onClick={handleClick}>
      <div className={iconContainer}>
        {icon}
      </div>
      <p className={titleContent}>{children}</p>
    </div>
  );
};
export default ProfilePortalContent;
