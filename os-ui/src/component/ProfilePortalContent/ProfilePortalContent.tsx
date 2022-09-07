import { useNavigate } from 'react-router-dom';
import { storage } from '../../services/storage/storage';
import styles from './ProfilePortalContent.module.scss';

const ProfilePortalContent = ({ icon, children, isSignOut }:any) => {
  const navigate = useNavigate();
  const { mainContent, iconContainer, titleContent } = styles;

  const handleClick = () => {
    if (isSignOut) {
      storage.removeItemFromLocalStorage('userInfo');
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
