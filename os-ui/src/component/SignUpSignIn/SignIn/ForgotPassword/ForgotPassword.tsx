import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { validateEmail } from '../../../../helpers/EmailValidate';
import authService from '../../../../services/authService';
import ResetPassword from '../ResetPassword/ResetPassword';
import CloseIcon from '../../../../icons/Close';
import styles from './ForgotPassword.module.scss';

const ForgotPassword = (
  { returnToSignInForm }: {returnToSignInForm: ()=> void},
) => {
  const { t } = useTranslation();
  const [email, setEmail] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [emailError, setEmailError] = useState('');
  const [resetPassword, setResetPassword] = useState(false);
  const { mainContainer, errorMessage, successedMessage } = styles;

  const handleEmailChange = (e:React.SyntheticEvent) => {
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
      timer = setTimeout(() => setResetPassword(true), 3500);
    }
    return () => clearTimeout(timer);
  }, [successMessage]);

  return (
    <>
      {
        resetPassword ? (
          <ResetPassword
            returnToSignInForm={() => returnToSignInForm()}
            email={email}
          />
        )
          : (
            <>
              {
              successMessage ? <h3 data-testid="forgotPasswordSuccessMessage" className={successedMessage}>{successMessage}</h3>
                : (
                  <>
                    <CloseIcon />
                    <div className={mainContainer}>
                      <h2>{t('string.forgotPsd.title')}</h2>
                      <p>{t('string.forgotPsd.enterEmail')}</p>
                      <p>{t('string.forgotPsd.sendNotification')}</p>
                      <div>
                        <label htmlFor="email">
                          {t('form.labels.email')}
                          <span style={{ color: 'red' }}> *</span>
                        </label>
                        <input
                          data-testid="forgotPasswordEmailInput"
                          id="email"
                          type="email"
                          name="email"
                          placeholder={t('form.placeholder.email')}
                          onChange={handleEmailChange}
                          required
                        />
                      </div>
                      {emailError ? (<h4 data-testid="emailErrorForgotPassword" className={errorMessage}>{emailError}</h4>) : null}
                      <button data-testid="continueButton" type="button" onClick={sendForgotPassword}>{t('button.forgotPsd.continue')}</button>
                    </div>
                  </>
                )
                }
            </>
          )
}
    </>
  );
};
export default ForgotPassword;
