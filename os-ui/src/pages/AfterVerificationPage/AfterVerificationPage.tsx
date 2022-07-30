import { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { addLoggedInUser } from '../../redux/Slices/loginUserSlice';
import authService from '../../services/authService';
import styles from './AfterVerificationPage.module.scss';
import { getCookie } from '../../helpers/GetCoockies';

const AfterVerificationPage = () => {
  const { t } = useTranslation();
  const { mainContainer } = styles;
  const [message, setMessage] = useState({ header: 'Please Wait...', content: '' });
  const dispatch = useDispatch();
  const navigate = useNavigate();
  useEffect(() => {
    const token = getCookie('verification-token');
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
  }, [authService, dispatch, navigate]);

  return (
    <div className={mainContainer}>
      <h1>{ message.header }</h1>
      <p>{ message.content }</p>
    </div>
  );
};
export default AfterVerificationPage;
