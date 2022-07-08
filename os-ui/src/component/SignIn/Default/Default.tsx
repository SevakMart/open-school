import { useState, useEffect, useRef } from 'react';
import { useDispatch } from 'react-redux';
import { validateSignInForm } from '../../../helpers/SignInFormValidate';
import authService from '../../../services/authService';
import { addLoggedInUser } from '../../../redux/Slices/loginUserSlice';
import VisibileIcon from '../../../icons/Visibility';
import HiddenIcon from '../../../icons/Hidden';
import { RegistrationFormType } from '../../../types/RegistartionFormType';
import { SIGN_IN, SUCCESSFUL_SIGNIN_MESSAGE } from '../../../constants/Strings';
import styles from './Default.module.scss';

const SignInDefault = ({ handleSignIn, forgotPasswordFunc }:
  {handleSignIn:(message:string)=>void, forgotPasswordFunc:()=>void}) => {
  const dispatch = useDispatch();
  const [formValues, setFormValues] = useState<RegistrationFormType>({
    firstName: '', lastName: '', email: '', psd: '',
  });
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

  const handleForgotPassword = () => forgotPasswordFunc();

  const handleSignInForm = () => {
    const { fullNameError, emailError, passwordError } = validateSignInForm(formValues);
    const { email, psd } = formValues;
    if (!fullNameError && !emailError && !passwordError) {
      authService.signIn({ email, psd }).then((response) => {
        if (response.status === 401) {
          setSignInErrorMessage(response.message);
          setErrorFormValue({ fullNameError: '', emailError: '', passwordError: '' });
        } else if (response.status === 200) {
          dispatch(addLoggedInUser(response));
          handleSignIn(SUCCESSFUL_SIGNIN_MESSAGE);
        }
      });
    } else {
      setErrorFormValue(validateSignInForm(formValues));
      setSignInErrorMessage('');
    }
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
        <label htmlFor="psd">
          Password
          <span style={{ color: 'red' }}>*</span>
        </label>
        <input
          id="psd"
          type="password"
          ref={passwordInputRef}
          value={formValues.psd}
          name="psd"
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
      <p onClick={handleForgotPassword}>Forgot Password?</p>
      <button type="button" data-testid="signInButton" onClick={handleSignInForm}>{SIGN_IN}</button>
    </form>
  );
};
export default SignInDefault;
