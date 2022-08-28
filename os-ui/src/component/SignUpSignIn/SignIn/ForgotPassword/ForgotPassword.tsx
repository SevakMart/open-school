import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import { validateEmail } from '../../../../helpers/EmailValidate';
import { openModal } from '../../../../redux/Slices/PortalOpenStatus';
import { Types } from '../../../../types/types';
import authService from '../../../../services/authService';
import Header from '../../Header/Header';
import Button from '../../../Button/Button';
import { Input } from '../../../Input/Input';
import styles from './ForgotPassword.module.scss';

const ForgotPassword = () => {
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const [email, setEmail] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [emailError, setEmailError] = useState('');
  const { mainContent, successedMessage } = styles;

  const handleInputChange = (e:React.SyntheticEvent) => {
    setEmail((e.target as HTMLInputElement).value);
  };

  const sendForgotPassword = () => {
    const emailError = (validateEmail(email));
    if (!emailError) {
      authService.sendForgotPasswordRequest({ email })
        .then((response) => {
          if (response.status === 200) {
            setSuccessMessage(response.data.message);
            setEmailError('');
          } else if (response.status === 400) {
            setEmailError(response.data.message);
          }
        });
    } else setEmailError(emailError);
  };
  useEffect(() => {
    let timer:any;
    if (successMessage) {
      timer = setTimeout(() => dispatch(openModal(Types.Button.RESET_PASSWORD)), 3500);
    }
    return () => clearTimeout(timer);
  }, [successMessage]);

  return (
    <>
      {successMessage ? <h3 data-testid="forgotPasswordSuccessMessage" className={successedMessage}>{successMessage}</h3>
        : (
          <>
            <Header
              mainTitle={t('string.forgotPsd.title')}
              shouldRemoveIconContent
              isForgotPasswordContent
            />
            <div className={mainContent}>
              <Input.EmailInput
                textName="email"
                labelText={t('form.labels.email')}
                errorMessage={emailError}
                placeholderText={t('form.placeholder.email')}
                value={email}
                handleInputChange={handleInputChange}
              />
              <Button.FormButton className={['formButton']} onClick={sendForgotPassword}>
                {t('button.forgotPsd.continue')}
              </Button.FormButton>
            </div>
          </>
        )}
    </>
  );
};
export default ForgotPassword;
