import { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { VERIFICATION_TOKEN_NOT_VALID_MESSAGE_CONTENT, VERIFICATION_TOKEN_NOT_VALID_MESSAGE_HEADER } from '../../constants/Strings';
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
          header: VERIFICATION_TOKEN_NOT_VALID_MESSAGE_HEADER,
          content: VERIFICATION_TOKEN_NOT_VALID_MESSAGE_CONTENT,
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
