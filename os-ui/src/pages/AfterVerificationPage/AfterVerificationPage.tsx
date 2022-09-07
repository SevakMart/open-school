import { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { addLoggedInUser } from '../../redux/Slices/loginUserSlice';
import authService from '../../services/authService';
import { storage } from '../../services/storage/storage';
import { Portal } from '../../component/Portal/Portal';
import styles from './AfterVerificationPage.module.scss';
import { getCookie } from '../../helpers/GetCoockies';

const AfterVerificationPage = () => {
  const { t } = useTranslation();
  const { mainContainer } = styles;
  const [message, setMessage] = useState({ header: t('string.afterVerification.loading'), content: '' });
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const userInfo = storage.getItemFromLocalStorage('userInfo');
  useEffect(() => {
    const token = getCookie('verification-token');
    let timer:any;
    authService.redirectUserToAccount({ token }).then((response) => {
      if (response.status === 200) {
        console.log(response);
        // dispatch(addLoggedInUser(response));
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

  return (
    <Portal.FormPortal isOpen>
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
