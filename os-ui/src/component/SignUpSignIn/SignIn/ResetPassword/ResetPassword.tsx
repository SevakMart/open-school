import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import { validateResetPasswordForm } from '../../../../helpers/ResetPasswordFormValidate';
import { closeModal, openModalWithSuccessMessage } from '../../../../redux/Slices/PortalOpenStatus';
import Form from '../../Form/Form';
import { Types } from '../../../../types/types';
import authService from '../../../../services/authService';
import { FormValues } from '../../../../types/FormTypes';
import './resetPassword.scss';

const initialErrorFormValues = { tokenError: '', newPasswordError: '', confirmedPasswordError: '' };

const ResetPassword = () => {
  const { t } = useTranslation();
  const forgotPasswordEmail = useSelector<RootState>((state) => state.forgotPasswordEmail);
  const dispatch = useDispatch();
  const [errorFormValue, setErrorFormValue] = useState(initialErrorFormValues);
  const email = useSelector<RootState>((state) => state.forgotPasswordEmail);

  const sendResetPassword = (formValues:FormValues) => {
    const { token, newPassword, confirmedPassword } = formValues;
    const { tokenError, newPasswordError, confirmedPasswordError } = validateResetPasswordForm(formValues);

    if (!tokenError && !newPasswordError && !confirmedPasswordError) {
      authService.sendResetPasswordRequest({ token, newPassword, confirmedPassword })
        .then((response) => {
          if (response.status === 200) {
            setErrorFormValue(initialErrorFormValues);
            dispatch(openModalWithSuccessMessage({ buttonType: Types.Button.SUCCESS_MESSAGE, withSuccessMessage: response.data.message, isResetPasswordSuccessfulMessage: true }));
            setTimeout(() => { dispatch(closeModal()); }, 2000);
          } else if (response.status === 400) {
            setErrorFormValue({ ...initialErrorFormValues, tokenError: response.data.message });
          }
        });
    } else setErrorFormValue(validateResetPasswordForm(formValues));
  };

  const resendEmail = () => {
    authService.sendForgotPasswordRequest({ forgotPasswordEmail })
      .then((response) => {
        dispatch(openModalWithSuccessMessage({ buttonType: Types.Button.SUCCESS_MESSAGE, withSuccessMessage: response.data.message }));
        setErrorFormValue(initialErrorFormValues);
      });
  };

  return (
    <>
      <div className="verPopUpTitle">{t('string.resetPsd.title')}</div>
      <div className="verPopupBody">
        <div className="sentMail">
          <div>{t('We sent your code to:')}</div>
          <div>{email}</div>
        </div>
      </div>
      <Form
        isSignUpForm={false}
        isResetPasswordForm
        formButtonText={t('button.resetPsd.submit')}
        errorFormValue={errorFormValue}
        handleForm={sendResetPassword}
        resendEmail={resendEmail}
      />
    </>
  );
};
export default ResetPassword;
