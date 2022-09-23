import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { removeUserInfoFromLocalStorage } from '../../redux/Slices/UserInfoSlice';
import styles from './ProfilePortalContent.module.scss';

const ProfilePortalContent = ({ icon, children, isSignOut }:any) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { mainContent, iconContainer, titleContent } = styles;

  const handleClick = () => {
    if (isSignOut) {
      dispatch(removeUserInfoFromLocalStorage());
      navigate('/');
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
