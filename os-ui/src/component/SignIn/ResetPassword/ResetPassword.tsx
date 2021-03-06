import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { validateResetPasswordForm } from '../../../helpers/ResetPasswordFormValidate';
import authService from '../../../services/authService';
import VisibileIcon from '../../../icons/Visibility';
import HiddenIcon from '../../../icons/Hidden';
import CloseIcon from '../../../icons/Close';
import styles from './ResetPassword.module.scss';

const ResetPassword = ({ returnToSignInForm, email }:
  {returnToSignInForm: ()=> void, email:string}) => {
  const { t } = useTranslation();
  const [successForgotPasswordMessage, setSuccessForgotPasswordMessage] = useState('');
  const [successResetPasswordMessage, setSuccessResetPasswordMessage] = useState('');
  const [isNewPasswordVisible, setIsNewPasswordVisible] = useState(false);
  const [isConfirmPasswordVisible, setIsConfirmPasswordVisible] = useState(false);
  const [formValues, setFormValues] = useState({ token: '', newPassword: '', confirmedPassword: '' });
  const [errorFormValue, setErrorFormValue] = useState({ tokenError: '', newPasswordError: '', confirmedPasswordError: '' });
  const newPasswordRef = useRef<null|HTMLInputElement>(null);
  const confirmPasswordRef = useRef<null|HTMLInputElement>(null);
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
      authService.sendResetPasswordRequest({ token, newPassword, confirmedPassword })
        .then((response) => {
          if (response.status === 200) {
            setErrorFormValue({ tokenError: '', newPasswordError: '', confirmedPasswordError: '' });
            setSuccessResetPasswordMessage(response.data.message);
          } else if (response.status === 400) {
            setErrorFormValue({ tokenError: response.data.message, newPasswordError: '', confirmedPasswordError: '' });
          }
        });
    } else setErrorFormValue(validateResetPasswordForm(formValues));
  };

  const resendEmail = () => {
    authService.sendForgotPasswordRequest({ email })
      .then((response) => {
        setErrorFormValue({ tokenError: '', newPasswordError: '', confirmedPasswordError: '' });
        setFormValues({ token: '', newPassword: '', confirmedPassword: '' });
        setSuccessForgotPasswordMessage(response.data.message);
      });
  };

  useEffect(() => {
    if (isNewPasswordVisible) {
      (newPasswordRef.current as HTMLInputElement).type = 'text';
    } else (newPasswordRef.current as HTMLInputElement).type = 'password';

    if (isConfirmPasswordVisible) {
      (confirmPasswordRef.current as HTMLInputElement).type = 'text';
    } else (confirmPasswordRef.current as HTMLInputElement).type = 'password';
  }, [isNewPasswordVisible, isConfirmPasswordVisible]);

  useEffect(() => {
    let timer:any;
    if (successResetPasswordMessage) {
      timer = setTimeout(() => returnToSignInForm(), 3500);
    }
    if (successForgotPasswordMessage) {
      timer = setTimeout(() => setSuccessForgotPasswordMessage(''), 3500);
    }
    return () => clearTimeout(timer);
  }, [successResetPasswordMessage, successForgotPasswordMessage]);

  return (
    <div className={mainContainer}>
      {successResetPasswordMessage
        ? <h3 data-testid="successResetPasswordMessage" className={successedResetPasswordMessage}>{successResetPasswordMessage}</h3>
        : successForgotPasswordMessage
          ? <h3 data-testid="successForgotPasswordMessage" className={successedResendEmailMessage}>{successForgotPasswordMessage}</h3> : (
            <>
              <CloseIcon handleClosing={() => returnToSignInForm()} />
              <h2>{t('string.resetPsd.title')}</h2>
              <div>
                <label htmlFor="token">
                  {t('form.labels.resetPsdToken')}
                  <span style={{ color: 'red' }}> *</span>
                </label>
                <input
                  id="token"
                  type="text"
                  name="token"
                  value={formValues.token}
                  placeholder={t('form.placeholder.resetPsdToken')}
                  data-testid="tokenInput"
                  onChange={handleOnChange}
                  required
                />
                {errorFormValue.tokenError
                  ? (<h4 data-testid="tokenErrorMessage" className={errorMessage}>{errorFormValue.tokenError}</h4>
                  ) : null}
              </div>
              <div>
                <label htmlFor="newPassword">
                  {t('form.labels.psd.reset')}
                  <span style={{ color: 'red' }}> *</span>
                </label>
                <input
                  id="newPassword"
                  type="password"
                  name="newPassword"
                  ref={newPasswordRef}
                  value={formValues.newPassword}
                  placeholder={t('form.placeholder.psd.reset')}
                  data-testid="psdInput"
                  onChange={handleOnChange}
                  required
                />
                {errorFormValue.newPasswordError
                  ? <h4 data-testid="newPasswordErrorMessage" className={errorMessage}>{errorFormValue.newPasswordError}</h4> : null}
                {isNewPasswordVisible
                  ? <VisibileIcon makeInvisible={handleNewPasswordVisibility} />
                  : <HiddenIcon makeVisible={handleNewPasswordVisibility} />}
              </div>
              <div>
                <label htmlFor="confirmedPassword">
                  {t('form.labels.psd.confirm')}
                  <span style={{ color: 'red' }}> *</span>
                </label>
                <input
                  id="confirmedPassword"
                  type="password"
                  name="confirmedPassword"
                  ref={confirmPasswordRef}
                  value={formValues.confirmedPassword}
                  placeholder={t('form.placeholder.psd.confirm')}
                  data-testid="confirmPsdInput"
                  onChange={handleOnChange}
                  required
                />
                {errorFormValue.confirmedPasswordError
                  ? (<h4 data-testid="confirmedPasswordErrorMessage" className={errorMessage}>{errorFormValue.confirmedPasswordError}</h4>
                  ) : null}
                {isConfirmPasswordVisible
                  ? <VisibileIcon makeInvisible={handleConfirmPasswordVisibility} />
                  : <HiddenIcon makeVisible={handleConfirmPasswordVisibility} />}
              </div>
              <button data-testid="resetPasswordButton" className={resetPasswordButton} type="button" onClick={sendResetPassword}>{t('button.resetPsd.submit')}</button>
              <button data-testid="resendEmailButton" className={resendEmailButton} type="button" onClick={resendEmail}>{t('button.resetPsd.resendEmail')}</button>
            </>
          )}
    </div>
  );
};
export default ResetPassword;
