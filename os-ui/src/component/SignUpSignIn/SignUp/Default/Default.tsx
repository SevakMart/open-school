import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import { addLoggedInUser } from '../../../../redux/Slices/loginUserSlice';
import { validateSignUpForm } from '../../../../helpers/SignUpFormValidate';
import { RegistrationFormType } from '../../../../types/RegistartionFormType';
import { Input } from '../../../Input/Input';
import Button from '../../../Button/Button';
import authService from '../../../../services/authService';
import styles from './Default.module.scss';

const SignUpDefault = ({ switchToSignInForm }:{switchToSignInForm:(message:string)=>void}) => {
  const dispatch = useDispatch();
  const { t } = useTranslation();
  const [formValues, setFormValues] = useState<RegistrationFormType>({
    firstName: '', lastName: '', email: '', psd: '',
  });
  const [errorFormValue, setErrorFormValue] = useState({
    firstNameError: '', lastNameError: '', emailError: '', psdError: '',
  });

  const { inputContent } = styles;

  const handleInputChange = (e:React.SyntheticEvent) => {
    setFormValues({
      ...formValues,
      [(e.target as HTMLInputElement).name]: (e.target as HTMLInputElement).value,
    });
  };

  const handleSubmitForm = () => {
    const {
      firstNameError, lastNameError, emailError, psdError,
    } = validateSignUpForm(formValues);
    if (!firstNameError && !lastNameError && !emailError && !psdError) {
      authService.register(formValues).then((response) => {
        if (response.status === 201) {
          setErrorFormValue({
            firstNameError: '', lastNameError: '', emailError: '', psdError: '',
          });
          setFormValues({
            firstName: '', lastName: '', email: '', psd: '',
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
    <form className={inputContent}>
      <Input.TextInput
        textName="firstName"
        labelText={t('form.labels.name')}
        errorMessage={errorFormValue.firstNameError}
        placeholderText={t('form.placeholder.name')}
        value={formValues.firstName}
        handleInputChange={handleInputChange}
      />
      <Input.TextInput
        textName="lastName"
        labelText={t('form.labels.surname')}
        errorMessage={errorFormValue.lastNameError}
        placeholderText={t('form.placeholder.surname')}
        value={formValues.lastName}
        handleInputChange={handleInputChange}
      />
      <Input.EmailInput
        textName="email"
        labelText={t('form.labels.email')}
        errorMessage={errorFormValue.emailError}
        placeholderText={t('form.placeholder.email')}
        value={formValues.email}
        handleInputChange={handleInputChange}
      />
      <Input.PasswordInput
        textName="psd"
        labelText={t('form.labels.psd.default')}
        errorMessage={errorFormValue.psdError}
        placeholderText={t('form.placeholder.psd.default')}
        value={formValues.psd}
        handleInputChange={handleInputChange}
      />
      <Button.SignUpButton className={['registerButton']} onClick={handleSubmitForm}>
        {t('button.homePage.signUp')}
      </Button.SignUpButton>
    </form>
  );
};
export default SignUpDefault;
