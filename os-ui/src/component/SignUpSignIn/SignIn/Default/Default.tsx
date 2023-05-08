import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { validateSignInForm } from '../../../../helpers/SignInFormValidate';
import authService from '../../../../services/authService';
import { closeModal } from '../../../../redux/Slices/PortalOpenStatus';
import { addUserInfoToLocalStorage } from '../../../../redux/Slices/UserInfoSlice';
import Form from '../../Form/Form';
import { FormValues } from '../../../../types/FormTypes';

const initialErrorFormValues = { fullNameError: '', emailError: '', passwordError: '' };

const SignInDefault = ({ handleSignIn }:{handleSignIn:()=>void}) => {
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const [errorFormValue, setErrorFormValue] = useState(initialErrorFormValues);
  const [signInErrorMessage, setSignInErrorMessage] = useState('');

  const handleSignInForm = (formValues:FormValues) => {
    const { fullNameError, emailError, passwordError } = validateSignInForm(formValues);
    const { email, psd } = formValues;
    if (!fullNameError && !emailError && !passwordError) {
      authService.signIn({ email, psd }).then((response) => {
        if (response.status === 401) {
          setSignInErrorMessage(response.message);
          setErrorFormValue(initialErrorFormValues);
        } else if (response.status === 200) {
          dispatch(addUserInfoToLocalStorage(response));
          handleSignIn();
          dispatch(closeModal());
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
      formButtonText={t('button.homePage.signIn')}
      errorFormValue={errorFormValue}
      handleForm={handleSignInForm}
      unAuthorizedSignInError={signInErrorMessage}
    />
  );
};
export default SignInDefault;
