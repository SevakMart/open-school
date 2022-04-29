import { useState, useEffect, useRef } from 'react';
import { useDispatch } from 'react-redux';
import { validateSignInForm } from '../../../helpers/SignInFormValidate';
import { signIn } from '../../../services/signIn';
import { addLoggedInUser } from '../../../redux/Slices/loginUserSlice';
import VisibileIcon from '../../../icons/Visibility';
import HiddenIcon from '../../../icons/Hidden';
import { RegistrationFormType } from '../../../types/RegistartionFormType';
import { SIGN_IN, SIGNIN_URL, SUCCESSFUL_SIGNIN_MESSAGE } from '../../../constants/Strings';
import styles from './Default.module.scss';

const SignInDefault = ({ handleSignIn }:{handleSignIn:(message:string)=>void}) => {
  const dispatch = useDispatch();
  const [formValues, setFormValues] = useState<RegistrationFormType>({ firstName: '', email: '', password: '' });
  const [errorFormValue, setErrorFormValue] = useState({ fullNameError: '', emailError: '', passwordError: '' });
  const [signInErrorMessage, setSignInErrorMessage] = useState('');
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

  const handleSignInForm = () => {
    const { fullNameError, emailError, passwordError } = validateSignInForm(formValues);
    const { email, password } = formValues;
    if (!fullNameError && !emailError && !passwordError) {
      signIn(SIGNIN_URL, { email, password }).then((response) => {
        if (response.status === 401) {
          setSignInErrorMessage(response.message);
        } else if (response.status === 200) {
          dispatch(addLoggedInUser(response));
          handleSignIn(SUCCESSFUL_SIGNIN_MESSAGE);
        }
      });
    } else setErrorFormValue(validateSignInForm(formValues));
  };

  useEffect(() => {
    if (isVisible) {
      (passwordInputRef.current as HTMLInputElement).type = 'text';
    } else (passwordInputRef.current as HTMLInputElement).type = 'password';
  }, [isVisible]);

  return (
    <form className={inputContent}>
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
      {signInErrorMessage ? <h4 data-testid="signInErrorMessage" className={errorField}>{signInErrorMessage}</h4> : null}
      <p>Forgot Password?</p>
      <button type="button" data-testid="signInButton" onClick={handleSignInForm}>{SIGN_IN}</button>
    </form>
  );
};
export default SignInDefault;