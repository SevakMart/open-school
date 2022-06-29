import { useState, useEffect, useRef } from 'react';
import { validateSignUpForm } from '../../../helpers/SignUpFormValidate';
import { RegistrationFormType } from '../../../types/RegistartionFormType';
import authService from '../../../services/authService';
import VisibileIcon from '../../../icons/Visibility';
import HiddenIcon from '../../../icons/Hidden';
import { SIGN_UP } from '../../../constants/Strings';
import styles from './Default.module.scss';

const SignUpDefault = ({ switchToSignInForm }:{switchToSignInForm:(message:string)=>void}) => {
  const [formValues, setFormValues] = useState<RegistrationFormType>({ firstName: '', email: '', password: '' });
  const [errorFormValue, setErrorFormValue] = useState({ fullNameError: '', emailError: '', passwordError: '' });
  const [isVisible, setIsVisible] = useState(false);
  const passwordInputRef = useRef<null|HTMLInputElement>(null);
  const { inputContent, errorField } = styles;
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
      authService.register(formValues).then((response) => {
        if (response.status === 400) {
          console.log(response.message);
          setErrorFormValue({ fullNameError: '', emailError: response.message, passwordError: '' });
        } else {
          setErrorFormValue({ fullNameError: '', emailError: '', passwordError: '' });
          setFormValues({ firstName: '', email: '', password: '' });
          switchToSignInForm!(response.message);
        }
      });
    } else setErrorFormValue(validateSignUpForm(formValues));
  };

  useEffect(() => {
    if (isVisible) {
      (passwordInputRef.current as HTMLInputElement).type = 'text';
    } else (passwordInputRef.current as HTMLInputElement).type = 'password';
  }, [isVisible]);

  return (
    <form className={inputContent}>
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
      <button type="button" data-testid="signUpButton" onClick={handleSubmitForm}>{SIGN_UP}</button>
    </form>
  );
};
export default SignUpDefault;
