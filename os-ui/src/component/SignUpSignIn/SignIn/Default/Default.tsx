import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { validateSignInForm } from '../../../../helpers/SignInFormValidate';
import authService from '../../../../services/authService';
import { addLoggedInUser } from '../../../../redux/Slices/loginUserSlice';
import Form, { FormValues } from '../../Form/Form';

const initialErrorFormValues = { fullNameError: '', emailError: '', passwordError: '' };

const SignInDefault = () => {
  const dispatch = useDispatch();
  const { t } = useTranslation();
  const navigate = useNavigate();
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
          dispatch(addLoggedInUser(response));
          navigate('/categories/subcategories');
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
    />
  );
};
export default SignInDefault;
