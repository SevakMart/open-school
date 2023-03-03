import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import { useState } from 'react';
import { openModalWithSuccessMessage } from '../../../redux/Slices/PortalOpenStatus';
import { Types } from '../../../types/types';
import Button from '../../Button/Button';
import Header from '../Header/Header';
import { validateEmail } from '../../../helpers/EmailValidate';
import authService from '../../../services/authService';
import { addEmail } from '../../../redux/Slices/ForgotPasswordEmailSlice';

type ForgotVerificationProps = {
	forgotVerficationEmail?: string
}
/* eslint-disable max-len */
const Verification = (props: ForgotVerificationProps) => {
  const { forgotVerficationEmail = '' } = props;
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const [emailError, setEmailError] = useState('');

  const handleResend = () => {
    const emailError = validateEmail(forgotVerficationEmail);
    if (!emailError) {
      authService.sendForgotVerificationRequest({ email: forgotVerficationEmail })
        .then((response) => {
          if (response.status === 200) {
            const successMessage = t('Verification Email Sent!');
            dispatch(openModalWithSuccessMessage({
              buttonType: Types.Button.SUCCESS_MESSAGE,
              withSuccessMessage: successMessage,
              isForgotVerificationMessage: true,
            }));
            dispatch(addEmail(forgotVerficationEmail));
            setEmailError('');
          }
        });
    } else setEmailError(emailError);
  };

  return (
    <>
      <Header
        mainTitle={t('string.onAccountVerification.title')}
        shouldRemoveIconContent
        isForgotPasswordContent={false}
        isVerificationContent
      />
      {emailError && <span style={{ color: 'red' }}>{emailError}</span>}
      <div style={{ margin: '0 80px' }}>
        <Button.FormButton className={['formButton']} onClick={handleResend}>
          {t('button.resend')}
        </Button.FormButton>
      </div>
    </>
  );
};
export default Verification;
