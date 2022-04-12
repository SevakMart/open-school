import React, { useState, useRef, useEffect } from 'react';
import HiddenIcon from '../../icons/Hidden';
import VisibileIcon from '../../icons/Visibility';
import { validateSignUpForm } from '../../helpers/SignUpFormValidate';
import { validateSignInForm } from '../../helpers/SignInFormValidate';
import {
  SIGN_UP, SIGN_IN, REGISTRATION_URL, SIGNIN_URL,
} from '../../constants/Strings';
import { register } from '../../services/register';
import { signIn } from '../../services/signIn';
import { RegistrationFormType } from '../../types/RegistartionFormType';
import styles from './SignUpSignInForm.module.scss';

interface FormProps{
  formType:string,
  switchToSignInForm?(message:string):void;
  handleSignIn?(message:string):void;
}

const Form = ({ formType, switchToSignInForm, handleSignIn }:FormProps) => {
  const [formValues, setFormValues] = useState<RegistrationFormType>({ firstName: '', email: '', password: '' });
  const [errorFormValue, setErrorFormValue] = useState({ fullNameError: '', emailError: '', passwordError: '' });
  const [signInErrorMMessage, setSignInErrorMessage] = useState('');
  const [isVisible, setIsVisible] = useState(false);
  const passwordInputRef = useRef<null|HTMLInputElement>(null);
  const { errorField } = styles;
  const handlePasswordVisibility = () => {
    setIsVisible((prevState) => !prevState);
  };

  const handleInputChange = (e:React.SyntheticEvent) => {
    setFormValues({
      ...formValues,
      [(e.target as HTMLInputElement).name]: (e.target as HTMLInputElement).value,
    });
  };
  const handleSubmitForm = async () => {
    const { fullNameError, emailError, passwordError } = validateSignUpForm(formValues);
    if (!fullNameError && !emailError && !passwordError) {
      const response = await register(REGISTRATION_URL, formValues);
      setErrorFormValue({ fullNameError: '', emailError: '', passwordError: '' });
      setFormValues({ firstName: '', email: '', password: '' });
      return switchToSignInForm!(response.message);
    } setErrorFormValue(validateSignUpForm(formValues));
  };

  const handleSignInForm = async () => {
    const { fullNameError, emailError, passwordError } = validateSignInForm(formValues);
    const { email, password } = formValues;
    if (!fullNameError && !emailError && !passwordError) {
      const response = await signIn(SIGNIN_URL, { email, password });
      if (response.status === 401) {
        setSignInErrorMessage(response.data.message);
      } else if (response.status === 200) {
        return handleSignIn!('You have Successfully signed in!');
      }
    } else setErrorFormValue(validateSignInForm(formValues));
  };

  useEffect(() => {
    if (isVisible) {
      (passwordInputRef.current as HTMLInputElement).type = 'text';
    } else (passwordInputRef.current as HTMLInputElement).type = 'password';
  }, [isVisible]);

  return (
    <>
      {formType === 'signUp'
        ? (
          <div>
            <label htmlFor="fullName">
              Full Name
              <span style={{ color: 'red' }}>*</span>
            </label>
            <input
              id="fullName"
              type="text"
              value={formValues.firstName}
              name="firstName"
              placeholder="Fill in first name"
              onChange={handleInputChange}
              required
            />
            {errorFormValue.fullNameError
              ? <h4 data-testid="fullnameErrorField" className={errorField}>{errorFormValue.fullNameError}</h4>
              : null}
          </div>
        ) : null}
      <div>
        <label htmlFor="email">
          Email
          <span style={{ color: 'red' }}>*</span>
        </label>
        <input
          id="email"
          type="email"
          value={formValues.email}
          name="email"
          placeholder="ex: namesurname@gmail.com"
          onChange={handleInputChange}
          required
        />
        {errorFormValue.emailError
          ? <h4 data-testid="emailErrorField" className={errorField}>{errorFormValue.emailError}</h4>
          : null}
      </div>
      <div>
        <label htmlFor="password">
          Password
          <span style={{ color: 'red' }}>*</span>
        </label>
        <input
          id="password"
          type="password"
          ref={passwordInputRef}
          value={formValues.password}
          name="password"
          placeholder="Enter your password"
          onChange={handleInputChange}
          required
        />
        {errorFormValue.passwordError
          ? <h4 data-testid="passwordErrorField" className={errorField}>{errorFormValue.passwordError}</h4>
          : null}
        {isVisible
          ? <VisibileIcon makeInvisible={handlePasswordVisibility} />
          : <HiddenIcon makeVisible={handlePasswordVisibility} />}
      </div>
      {signInErrorMMessage ? <h4 className={errorField}>{signInErrorMMessage}</h4> : null}
      <p>Forgot Password?</p>
      {formType === 'signUp' ? <button type="button" data-testid="signUpButton" onClick={handleSubmitForm}>{SIGN_UP}</button>
        : <button type="button" data-testid="signInButton" onClick={handleSignInForm}>{SIGN_IN}</button>}
    </>
  );
};
export default Form;
