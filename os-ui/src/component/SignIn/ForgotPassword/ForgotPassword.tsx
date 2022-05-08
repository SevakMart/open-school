import React, { useEffect, useState } from 'react';
import { sendForgotPasswordRequest } from '../../../services/sendForgotPasswordRequest';
import { validateEmail } from '../../../helpers/EmailValidate';
import ResetPassword from '../ResetPassword/ResetPassword';
import CloseIcon from '../../../icons/Close';
import styles from './ForgotPassword.module.scss';

const ForgotPassword = (
  { returnToSignInForm }: {returnToSignInForm: ()=> void},
) => {
  const [email, setEmail] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [failedMessage, setFailedMessage] = useState('');
  const [emailError, setEmailError] = useState('');
  const [resetPassword, setResetPassword] = useState(false);
  const { mainContainer, errorMessage, successedMessage } = styles;

  const handleEmailChange = (e:React.SyntheticEvent) => {
    setEmail((e.target as HTMLInputElement).value);
  };

  const sendForgotPassword = () => {
    const emailError = (validateEmail(email));
    if (!emailError) {
      setEmailError('');
      sendForgotPasswordRequest('http://localhost:5000/api/v1/auth/password/forgot', email)
        .then((response) => {
          if (response.status === 200) {
            setSuccessMessage(response.data.message);
          } else if (response.status === 400) {
            setFailedMessage(response.data.message);
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
                      <h2>Forgot password</h2>
                      <p>
                        Enter your email for the verification process.
                      </p>
                      <p>We will send 4 digits code to your email.</p>
                      <div>
                        <label htmlFor="email">
                          Email
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
                      {emailError || failedMessage ? (
                        <h4 className={errorMessage}>
                          {emailError || failedMessage}
                        </h4>
                      ) : null}
                      <button type="button" onClick={sendForgotPassword}>Continue</button>
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
