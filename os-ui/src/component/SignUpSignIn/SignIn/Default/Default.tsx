import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { validateSignInForm } from '../../../../helpers/SignInFormValidate';
import authService from '../../../../services/authService';
import { addLoggedInUser } from '../../../../redux/Slices/loginUserSlice';
import Form, { FormValues } from '../../Form/Form';

const SignInDefault = ({ handleSignIn }:
  {handleSignIn:()=>void }) => {
  const dispatch = useDispatch();
  const [errorFormValue, setErrorFormValue] = useState({ fullNameError: '', emailError: '', passwordError: '' });
  const [signInErrorMessage, setSignInErrorMessage] = useState('');

  const handleSignInForm = (formValues:FormValues) => {
    const { fullNameError, emailError, passwordError } = validateSignInForm(formValues);
    const { email, psd } = formValues;
    if (!fullNameError && !emailError && !passwordError) {
      authService.signIn({ email, psd }).then((response) => {
        if (response.status === 401) {
          setSignInErrorMessage(response.message);
          setErrorFormValue({ fullNameError: '', emailError: '', passwordError: '' });
        } else if (response.status === 200) {
          dispatch(addLoggedInUser(response));
          handleSignIn();
        }
      });
    } else {
      setErrorFormValue(validateSignInForm(formValues));
      setSignInErrorMessage('');
    }
  };

  return (
    <Form
      isSignUpForm={false}
      isResetPasswordForm={false}
      formButtonText="Sign In"
      errorFormValue={errorFormValue}
      handleForm={handleSignInForm}
    />
  );
};
export default SignInDefault;
