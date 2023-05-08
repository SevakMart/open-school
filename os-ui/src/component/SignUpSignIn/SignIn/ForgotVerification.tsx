import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import { validateEmail } from '../../../helpers/EmailValidate';
import { addEmail } from '../../../redux/Slices/ForgotPasswordEmailSlice';
import { openModalWithSuccessMessage } from '../../../redux/Slices/PortalOpenStatus';
import authService from '../../../services/authService';
import { Types } from '../../../types/types';
import Button from '../../Button/Button';
import { Input } from '../../Input/Input';
import Header from '../Header/Header';

type ForgotVerificationProps = {
	forgotVerficationEmail?: string
}

const ForgotVerification = (props: ForgotVerificationProps) => {
  const { forgotVerficationEmail } = props;

  const { t } = useTranslation();
  const dispatch = useDispatch();
  const [email, setEmail] = useState(forgotVerficationEmail || '');
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

  const sendForgotVerification = () => {
    const emailError = validateEmail(email);
    if (!emailError) {
      authService.sendForgotVerificationRequest({ email })
        .then((response) => {
          if (response.status === 200) {
            const successMessage = t('Verification Email Sent!');
            dispatch(openModalWithSuccessMessage({
              buttonType: Types.Button.SUCCESS_MESSAGE,
              withSuccessMessage: successMessage,
              isForgotVerificationMessage: true,
            }));
            dispatch(addEmail(email));
            setEmailError('');
          }
        });
    } else setEmailError(emailError);
  };

  return (
    <>
      <Header
        mainTitle={t('Your verification link has expired, enter mail to verify your account')}
        shouldRemoveIconContent
        isForgotPasswordContent={false}
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
        <Button.FormButton className={['formButton']} onClick={sendForgotVerification}>
          {t('Resend')}
        </Button.FormButton>
      </div>
    </>
  );
};

export default ForgotVerification;
