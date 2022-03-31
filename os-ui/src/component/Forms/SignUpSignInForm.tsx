import React, { useState, useRef, useEffect } from 'react';
import HiddenIcon from '../../icons/Hidden';
import VisibileIcon from '../../icons/Visibility';
import { validateSignUpForm } from '../../helpers/SignUpFormValidate';
import { validateSignInForm } from '../../helpers/SignInFormValidate';
import { SIGN_UP, SIGN_IN, REGISTRATION_URL } from '../../constants/Strings';
import { register } from '../../services/register';
import { RegistrationFormType } from '../../types/RegistartionFormType';
import styles from './SignUpSignInForm.module.scss';

const Form = ({ formType }:{formType:string}) => {
  const [formValues, setFormValues] = useState<RegistrationFormType>({ firstName: '', email: '', password: '' });
  const [errorFormValue, setErrorFormValue] = useState({ fullNameError: '', emailError: '', passwordError: '' });
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
  const handleSubmitForm = () => {
    const { fullNameError, emailError, passwordError } = validateSignUpForm(formValues);
    if (!fullNameError && !emailError && !passwordError) {
      register(REGISTRATION_URL, formValues);
      setErrorFormValue({ fullNameError: '', emailError: '', passwordError: '' });
      setFormValues({ firstName: '', email: '', password: '' });
    } else setErrorFormValue(validateSignUpForm(formValues));
  };

  const handleSignInForm = () => {
    const { fullNameError, emailError, passwordError } = validateSignInForm(formValues);
    if (!fullNameError && !emailError && !passwordError) {
      console.log('Your form will be authenticated');
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
      <p>Forgot Password?</p>
      {formType === 'signUp' ? <button type="button" data-testid="signUpButton" onClick={handleSubmitForm}>{SIGN_UP}</button>
        : <button type="button" data-testid="signInButton" onClick={handleSignInForm}>{SIGN_IN}</button>}
    </>
  );
};
export default Form;
