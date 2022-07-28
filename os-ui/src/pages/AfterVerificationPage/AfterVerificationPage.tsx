import { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { addLoggedInUser } from '../../redux/Slices/loginUserSlice';
import authService from '../../services/authService';
import styles from './AfterVerificationPage.module.scss';

const AfterVerificationPage = () => {
  function getCookie(name: string) {
    const nameEQ = `${name}=`;
    const ca = document.cookie.split(';');
    for (let c of ca) {
      while (c.charAt(0) === ' ') c = c.substring(1, c.length);
      if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
  }
  const { t } = useTranslation();
  const { mainContainer } = styles;
  const [message, setMessage] = useState({ header: 'Please Wait...', content: '' });
  const token = getCookie('verification-token');
  const dispatch = useDispatch();
  const navigate = useNavigate();
  useEffect(() => {
    authService.redirectUserToAccount({ token }).then((response) => {
      if (response.status === 200) {
        dispatch(addLoggedInUser(response));
        navigate('/categories/subcategories');
      } else {
        setMessage({
          header: t('string.afterVerification.verificationTokenNotValidMessageHeader'),
          content: t('string.afterVerification.verificationTokenNotValidMessageContent'),
        });
      }
    });
  }, [authService]);

  return (
    <div className={mainContainer}>
      <h1>{ message.header }</h1>
      <p>{ message.content }</p>
    </div>
  );
};
export default AfterVerificationPage;
