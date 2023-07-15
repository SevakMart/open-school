import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import { validateEmail } from '../../../../helpers/EmailValidate';
import { openModal, openModalWithSuccessMessage } from '../../../../redux/Slices/PortalOpenStatus';
import { addEmail } from '../../../../redux/Slices/ForgotPasswordEmailSlice';
import { Types } from '../../../../types/types';
import authService from '../../../../services/authService';
import Header from '../../Header/Header';
import { Input } from '../../../Input/Input';
import './forgotPassword.scss';

const ForgotPassword = () => {
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const [email, setEmail] = useState('');
  const [emailError, setEmailError] = useState('');

  const handleInputChange = (e:React.SyntheticEvent) => {
    setEmail((e.target as HTMLInputElement).value);
  };

  const sendForgotPassword = () => {
    const emailError = (validateEmail(email));
    if (!emailError) {
      authService.sendForgotPasswordRequest({ email })
        .then((response) => {
          if (response.status === 200) {
            dispatch(openModalWithSuccessMessage({ buttonType: Types.Button.Code_VerifivationPopup, withSuccessMessage: response.data.message }));
            dispatch(addEmail(email));
            setEmailError('');
          } else if (response.status === 400) {
            setEmailError(response.data.message);
          }
        });
    } else setEmailError(emailError);
  };

  const toSomePopup = () => {
    dispatch(openModal({ buttonType: Types.Button.SIGN_IN }));
  };

  return (
    <div className="forgotpsdContainer">
      <Header
        mainTitle={t('string.forgotPsd.title')}
        shouldRemoveIconContent
        isForgotPasswordContent
        isVerificationContent={false}
      />
      <div className="forgotpsdContainer_mainBody">
        <Input.TextInput
          textName=""
          labelText={t('form.labels.email')}
          errorMessage={emailError}
          placeholderText={t('form.placeholder.email')}
          value={email}
          handleInputChange={handleInputChange}
        />
        <div className="ForgotPasswordBtns">
          <button type="button" className="anyBtn backButton" onClick={toSomePopup}>Go Back</button>
          <button type="button" className="anyBtn continueButton" onClick={sendForgotPassword}>Continue</button>
        </div>
      </div>
    </div>
  );
};
export default ForgotPassword;
