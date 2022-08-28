import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import { addLoggedInUser } from '../../../../redux/Slices/loginUserSlice';
import { validateSignUpForm } from '../../../../helpers/SignUpFormValidate';
import { RegistrationFormType } from '../../../../types/RegistartionFormType';
import { Input } from '../../../Input/Input';
import Button from '../../../Button/Button';
import Form, { FormValues } from '../../Form/Form';
import authService from '../../../../services/authService';

import styles from './Default.module.scss';

const SignUpDefault = ({ switchToSignInForm }:{switchToSignInForm:(message:string)=>void}) => {
  const dispatch = useDispatch();
  const { t } = useTranslation();
  /* const [formValues, setFormValues] = useState<RegistrationFormType>({
    firstName: '', lastName: '', email: '', psd: '',
  }); */
  const [errorFormValue, setErrorFormValue] = useState({
    firstNameError: '', lastNameError: '', emailError: '', psdError: '',
  });

  /* const { inputContent } = styles;

  const handleInputChange = (e:React.SyntheticEvent) => {
    setFormValues({
      ...formValues,
      [(e.target as HTMLInputElement).name]: (e.target as HTMLInputElement).value,
    });
  }; */

  const handleSubmitForm = (formValues:FormValues) => {
    const {
      firstNameError, lastNameError, emailError, psdError,
    } = validateSignUpForm(formValues);
    if (!firstNameError && !lastNameError && !emailError && !psdError) {
      authService.register(formValues).then((response) => {
        if (response.status === 201) {
          setErrorFormValue({
            firstNameError: '', lastNameError: '', emailError: '', psdError: '',
          });
          dispatch(addLoggedInUser(response));
          switchToSignInForm!(t('messages.successfullSignUp'));
        } else {
          setErrorFormValue({
            firstNameError: '', lastNameError: '', emailError: response[0], psdError: '',
          });
        }
      });
    } else setErrorFormValue(validateSignUpForm(formValues));
  };

  return (
    <Form
      isSignUpForm
      isResetPasswordForm={false}
      formButtonText="Sign Up"
      errorFormValue={errorFormValue}
      handleForm={handleSubmitForm}
    />
  );
};
export default SignUpDefault;
