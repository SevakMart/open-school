import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import authService from '../../services/authService';
import { storage } from '../../services/storage/storage';
import { Portal } from '../../component/Portal/Portal';
import styles from './AfterVerificationPage.module.scss';
import { getCookie } from '../../helpers/GetCoockies';
import CloseIcon from '../../icons/Close';
import { closeModal } from '../../redux/Slices/PortalOpenStatus';

const AfterVerificationPage = () => {
  const { t } = useTranslation();
  const { mainContainer, closeIcon } = styles;
  const [message, setMessage] = useState({ header: t('string.afterVerification.loading'), content: '' });
  const navigate = useNavigate();
  useEffect(() => {
    const token = getCookie('verification-token');
    let timer:any;
    authService.redirectUserToAccount({ token }).then((response) => {
      if (response.status === 200) {
        storage.addItemToLocalStorage('userInfo', response as any);
        timer = setTimeout(() => navigate('/categories/subcategories'), 3000);
      } else {
        setMessage({
          header: t('string.afterVerification.verificationTokenNotValidMessageHeader'),
          content: t('string.afterVerification.verificationTokenNotValidMessageContent'),
        });
      }
    });
    return () => clearTimeout(timer);
  }, []);
  const dispatch = useDispatch();
  const handleClosePortal = () => {
    dispatch(closeModal());
    navigate('/homepage');
  };

  return (
    <Portal.FormPortal isOpen>
      <div className={closeIcon} onClick={handleClosePortal}><CloseIcon /></div>
      <div className={mainContainer}>
        <h1>
          { message.header }
          ...
        </h1>
        {message.content !== '' && <p>{ message.content }</p>}
      </div>
    </Portal.FormPortal>

  );
};
export default AfterVerificationPage;
