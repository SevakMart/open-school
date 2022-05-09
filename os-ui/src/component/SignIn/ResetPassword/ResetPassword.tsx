import React, { useEffect, useState, useRef } from 'react';
import { sendResetPasswordRequest } from '../../../services/sendResetPasswordRequest';
import { sendForgotPasswordRequest } from '../../../services/sendForgotPasswordRequest';
import { validateResetPasswordForm } from '../../../helpers/ResetPasswordFormValidate';
import {
  RESET_PASSWORD_URL, FORGOT_PASSWORD_URL, RESET_PASSWORD, VERIFICATION_CODE,
  NEW_PASSWORD, CONFIRM_PASSWORD, RESEND_EMAIL,
} from '../../../constants/Strings';
import VisibileIcon from '../../../icons/Visibility';
import HiddenIcon from '../../../icons/Hidden';
import CloseIcon from '../../../icons/Close';
import styles from './ResetPassword.module.scss';

const ResetPassword = ({ returnToSignInForm, email }:
  {returnToSignInForm: ()=> void, email:string}) => {
  const [successForgotPasswordMessage, setSuccessForgotPasswordMessage] = useState('');
  const [successResetPasswordMessage, setSuccessResetPasswordMessage] = useState('');
  const [failedMessage, setFailedMessage] = useState('');
  const [isNewPasswordVisible, setIsNewPasswordVisible] = useState(false);
  const [isConfirmPasswordVisible, setIsConfirmPasswordVisible] = useState(false);
  const newPasswordRef = useRef<null|HTMLInputElement>(null);
  const confirmPasswordRef = useRef<null|HTMLInputElement>(null);
  const [formValues, setFormValues] = useState({ token: '', newPassword: '', confirmedPassword: '' });
  const [errorFormValue, setErrorFormValue] = useState({ tokenError: '', newPasswordError: '', confirmedPasswordError: '' });
  const {
    mainContainer, errorMessage, successedResetPasswordMessage, successedResendEmailMessage,
    resetPasswordButton, resendEmailButton,
  } = styles;

  const handleNewPasswordVisibility = () => {
    setIsNewPasswordVisible((prevState) => !prevState);
  };

  const handleConfirmPasswordVisibility = () => {
    setIsConfirmPasswordVisible((prevState) => !prevState);
  };

  const handleOnChange = (e:React.SyntheticEvent) => {
    setFormValues({
      ...formValues,
      [(e.target as HTMLInputElement).name]: (e.target as HTMLInputElement).value,
    });
  };

  const sendResetPassword = () => {
    const { token, newPassword, confirmedPassword } = formValues;
    const {
      tokenError, newPasswordError,
      confirmedPasswordError,
    } = validateResetPasswordForm(formValues);
    if (!tokenError && !newPasswordError && !confirmedPasswordError) {
      setErrorFormValue({ tokenError: '', newPasswordError: '', confirmedPasswordError: '' });
      sendResetPasswordRequest(`${RESET_PASSWORD_URL}`, { token, newPassword, confirmedPassword }).then((response) => {
        if (response.status === 200) {
          setSuccessResetPasswordMessage(response.data.message);
        } else if (response.status === 400) {
          if (response.data.message === ('Invalid token' || 'Token is expired')) { setFailedMessage(response.data.message); }
        }
      });
    } else setErrorFormValue(validateResetPasswordForm(formValues));
  };

  const resendEmail = () => {
    sendForgotPasswordRequest(`${FORGOT_PASSWORD_URL}`, email)
      .then((response) => {
        setSuccessForgotPasswordMessage(response.data.message);
      });
  };

  useEffect(() => {
    if (isNewPasswordVisible) {
      (newPasswordRef.current as HTMLInputElement).type = 'text';
    } else (newPasswordRef.current as HTMLInputElement).type = 'password';
  }, [isNewPasswordVisible]);

  useEffect(() => {
    if (isConfirmPasswordVisible) {
      (confirmPasswordRef.current as HTMLInputElement).type = 'text';
    } else (confirmPasswordRef.current as HTMLInputElement).type = 'password';
  }, [isConfirmPasswordVisible]);

  useEffect(() => {
    let timer:any;
    if (successResetPasswordMessage) {
      timer = setTimeout(() => returnToSignInForm(), 3500);
    }
    return () => clearTimeout(timer);
  }, [successResetPasswordMessage]);

  useEffect(() => {
    let timer:any;
    if (successForgotPasswordMessage) {
      timer = setTimeout(() => setSuccessForgotPasswordMessage(''), 3500);
    }
    return () => clearTimeout(timer);
  }, [successForgotPasswordMessage]);

  return (
    <div className={mainContainer}>
      {successResetPasswordMessage
        ? <h3 className={successedResetPasswordMessage}>{successResetPasswordMessage}</h3>
        : successForgotPasswordMessage
          ? <h3 className={successedResendEmailMessage}>{successForgotPasswordMessage}</h3> : (
            <>
              <CloseIcon handleClosing={() => returnToSignInForm()} />
              <h2>{RESET_PASSWORD}</h2>
              <div>
                <label htmlFor="token">
                  {VERIFICATION_CODE}
                  <span style={{ color: 'red' }}> *</span>
                </label>
                <input
                  id="token"
                  type="text"
                  name="token"
                  value={formValues.token}
                  placeholder="Enter the 4 digits code that you received on your email"
                  onChange={handleOnChange}
                  required
                />
                {errorFormValue.tokenError || failedMessage
                  ? (
                    <h4 className={errorMessage}>
                      {errorFormValue.tokenError || failedMessage}
                    </h4>
                  ) : null}
              </div>
              <div>
                <label htmlFor="newPassword">
                  {NEW_PASSWORD}
                  <span style={{ color: 'red' }}> *</span>
                </label>
                <input
                  id="newPassword"
                  type="password"
                  name="newPassword"
                  ref={newPasswordRef}
                  value={formValues.newPassword}
                  placeholder="Enter the new password"
                  onChange={handleOnChange}
                  required
                />
                {errorFormValue.newPasswordError
                  ? <h4 className={errorMessage}>{errorFormValue.newPasswordError}</h4> : null}
                {isNewPasswordVisible
                  ? <VisibileIcon makeInvisible={handleNewPasswordVisibility} />
                  : <HiddenIcon makeVisible={handleNewPasswordVisibility} />}
              </div>
              <div>
                <label htmlFor="confirmedPassword">
                  {CONFIRM_PASSWORD}
                  <span style={{ color: 'red' }}> *</span>
                </label>
                <input
                  id="confirmedPassword"
                  type="password"
                  name="confirmedPassword"
                  ref={confirmPasswordRef}
                  value={formValues.confirmedPassword}
                  placeholder="Confirm the password"
                  onChange={handleOnChange}
                  required
                />
                {errorFormValue.confirmedPasswordError
                  ? (
                    <h4 className={errorMessage}>
                      {errorFormValue.confirmedPasswordError}
                    </h4>
                  ) : null}
                {isConfirmPasswordVisible
                  ? <VisibileIcon makeInvisible={handleConfirmPasswordVisibility} />
                  : <HiddenIcon makeVisible={handleConfirmPasswordVisibility} />}
              </div>
              <button className={resetPasswordButton} type="button" onClick={sendResetPassword}>{RESET_PASSWORD}</button>
              <button className={resendEmailButton} type="button" onClick={resendEmail}>{RESEND_EMAIL}</button>
            </>
          )}
    </div>
  );
};
export default ResetPassword;
