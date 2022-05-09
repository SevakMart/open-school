import React, { useEffect, useState } from 'react';
import { sendForgotPasswordRequest } from '../../../services/sendForgotPasswordRequest';
import { validateEmail } from '../../../helpers/EmailValidate';
import ResetPassword from '../ResetPassword/ResetPassword';
import {
  EMAIL, FORGOT_PASSWORD, ENTER_EMAIL_FOR_VERIFICATION, SEND_CODE_NOTIFICATION,
  CONTINUE, FORGOT_PASSWORD_URL,
} from '../../../constants/Strings';
import CloseIcon from '../../../icons/Close';
import styles from './ForgotPassword.module.scss';

const ForgotPassword = (
  { returnToSignInForm }: {returnToSignInForm: ()=> void},
) => {
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
      sendForgotPasswordRequest(`${FORGOT_PASSWORD_URL}`, email)
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
              successMessage ? <h3 className={successedMessage}>{successMessage}</h3>
                : (
                  <>
                    <CloseIcon handleClosing={() => returnToSignInForm()} />
                    <div className={mainContainer}>
                      <h2>{FORGOT_PASSWORD}</h2>
                      <p>{ENTER_EMAIL_FOR_VERIFICATION}</p>
                      <p>{SEND_CODE_NOTIFICATION}</p>
                      <div>
                        <label htmlFor="email">
                          {EMAIL}
                          <span style={{ color: 'red' }}> *</span>
                        </label>
                        <input
                          id="email"
                          type="email"
                          name="email"
                          placeholder="ex: namesurname@gmail.com"
                          onChange={handleEmailChange}
                          required
                        />
                      </div>
                      {emailError ? (<h4 className={errorMessage}>{emailError}</h4>) : null}
                      <button type="button" onClick={sendForgotPassword}>{CONTINUE}</button>
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
