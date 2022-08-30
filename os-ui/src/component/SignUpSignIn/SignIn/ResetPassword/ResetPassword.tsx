import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import { validateResetPasswordForm } from '../../../../helpers/ResetPasswordFormValidate';
import { openModalWithSuccessMessage } from '../../../../redux/Slices/PortalOpenStatus';
import Form, { FormValues } from '../../Form/Form';
import { Types } from '../../../../types/types';
import authService from '../../../../services/authService';

const initialErrorFormValues = { tokenError: '', newPasswordError: '', confirmedPasswordError: '' };
/* eslint-disable max-len */

const ResetPassword = () => {
  const { t } = useTranslation();
  const forgotPasswordEmail = useSelector<RootState>((state) => state.forgotPasswordEmail);
  const dispatch = useDispatch();
  const [errorFormValue, setErrorFormValue] = useState(initialErrorFormValues);

  const sendResetPassword = (formValues:FormValues) => {
    const { token, newPassword, confirmedPassword } = formValues;
    const { tokenError, newPasswordError, confirmedPasswordError } = validateResetPasswordForm(formValues);
    if (!tokenError && !newPasswordError && !confirmedPasswordError) {
      authService.sendResetPasswordRequest({ token, newPassword, confirmedPassword })
        .then((response) => {
          if (response.status === 200) {
            setErrorFormValue(initialErrorFormValues);
            dispatch(openModalWithSuccessMessage({
              buttonType: Types.Button.SUCCESS_MESSAGE, withSuccessMessage: response.data.message, isSignUpSuccessfulRegistration: false, isResetPasswordSuccessfulMessage: true,
            }));
          } else if (response.status === 400) {
            setErrorFormValue({ ...initialErrorFormValues, tokenError: response.data.message });
          }
        });
    } else setErrorFormValue(validateResetPasswordForm(formValues));
  };

  const resendEmail = () => {
    authService.sendForgotPasswordRequest({ forgotPasswordEmail })
      .then((response) => {
        dispatch(openModalWithSuccessMessage({ buttonType: Types.Button.SUCCESS_MESSAGE, withSuccessMessage: response.data.message, isSignUpSuccessfulRegistration: false }));
        setErrorFormValue(initialErrorFormValues);
      });
  };

  return (
    <Form
      isSignUpForm={false}
      isResetPasswordForm={false}
      formButtonText={t('button.homePage.signIn')}
      errorFormValue={errorFormValue}
      handleForm={sendResetPassword}
      resendEmail={resendEmail}
    />
  );
};
export default ResetPassword;
