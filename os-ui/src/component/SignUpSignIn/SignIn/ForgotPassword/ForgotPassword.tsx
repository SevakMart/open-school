import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import { validateEmail } from '../../../../helpers/EmailValidate';
import { openModalWithSuccessMessage } from '../../../../redux/Slices/PortalOpenStatus';
import { addEmail } from '../../../../redux/Slices/ForgotPasswordEmailSlice';
import { Types } from '../../../../types/types';
import authService from '../../../../services/authService';
import Header from '../../Header/Header';
import Button from '../../../Button/Button';
import { Input } from '../../../Input/Input';

const ForgotPassword = () => {
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const [email, setEmail] = useState('');
  const [emailError, setEmailError] = useState('');

  const handleInputChange = (e:React.SyntheticEvent) => {
    setEmail((e.target as HTMLInputElement).value);
  };

  const handleFormOnClick = (e: React.SyntheticEvent) => {
    e.preventDefault();
  };

  const handleSubmit = (event: React.KeyboardEvent<HTMLInputElement>): void => {
    if (event.key === 'Enter') {
	  event.preventDefault();
	  handleFormOnClick(event);
    }
  };

  const sendForgotPassword = () => {
    const emailError = (validateEmail(email));
    if (!emailError) {
      authService.sendForgotPasswordRequest({ email })
        .then((response) => {
          if (response.status === 200) {
            dispatch(openModalWithSuccessMessage({ buttonType: Types.Button.SUCCESS_MESSAGE, withSuccessMessage: response.data.message }));
            dispatch(addEmail(email));
            setEmailError('');
          } else if (response.status === 400) {
            setEmailError(response.data.message);
          }
        });
    } else setEmailError(emailError);
  };

  return (
    <>
      <Header
        mainTitle={t('string.forgotPsd.title')}
        shouldRemoveIconContent
        isForgotPasswordContent
        isVerificationContent={false}
      />
      <div style={{ margin: '0 80px' }}>
        <Input.EmailInput
          textName="email"
          labelText={t('form.labels.email')}
          errorMessage={emailError}
          placeholderText={t('form.placeholder.email')}
          value={email}
          handleInputChange={handleInputChange}
          handleEnterPress={handleSubmit}
        />
        <Button.FormButton className={['formButton']} onClick={sendForgotPassword}>
          {t('button.forgotPsd.continue')}
        </Button.FormButton>
      </div>

    </>
  );
};
export default ForgotPassword;
