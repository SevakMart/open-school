import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Input } from '../../Input/Input';
import Button from '../../Button/Button';
import styles from './Form.module.scss';

export interface FormValues {
    [index:string]:string
}
interface ErrorFormValues {
    [index:string]:string
}

/* eslint-disable max-len */
const Form = ({
  isSignUpForm, isResetPasswordForm, formButtonText, errorFormValue, handleForm, handleForgotPassword,
}:
    {isSignUpForm:boolean, isResetPasswordForm:boolean, formButtonText:string, errorFormValue:ErrorFormValues, handleForm:(formValue:FormValues)=>void, handleForgotPassword?:()=>void}) => {
  const [formValues, setFormValues] = useState({
    firstName: '', lastName: '', email: '', psd: '', token: '', newPassword: '', confirmedPassword: '',
  });
  const { t } = useTranslation();
  const { inputContent, forgotPassword } = styles;

  const handleInputChange = (e:React.SyntheticEvent) => {
    setFormValues({
      ...formValues,
      [(e.target as HTMLInputElement).name]: (e.target as HTMLInputElement).value,
    });
  };

  const handleFormOnClick = () => {
    const newFormValues = Object.fromEntries(Object.entries(formValues).filter((formValue) => formValue[1] !== ''));
    handleForm(newFormValues);
  };

  return (
    <form className={inputContent}>
      {isSignUpForm
    && (
    <>
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
    </>
    )}
      <Input.EmailInput
        textName="email"
        labelText={t('form.labels.email')}
        errorMessage={errorFormValue.emailError}
        placeholderText={t('form.placeholder.email')}
        value={formValues.email}
        handleInputChange={handleInputChange}
      />
      <Input.PasswordInput
        textName={isResetPasswordForm ? 'newPassword' : 'psd'}
        labelText={isResetPasswordForm ? t('form.labels.psd.reset') : t('form.labels.psd.default')}
        errorMessage={isResetPasswordForm ? errorFormValue.newPasswordError : errorFormValue.psdError}
        placeholderText={isResetPasswordForm ? t('form.placeholder.psd.reset') : t('form.placeholder.psd.default')}
        value={isResetPasswordForm ? formValues.newPassword : formValues.psd}
        handleInputChange={handleInputChange}
      />
      {isResetPasswordForm
        && (
        <Input.PasswordInput
          textName="confirmedPassword"
          labelText={t('form.labels.psd.confirm')}
          errorMessage={errorFormValue.confirmedPasswordError}
          placeholderText={t('form.placeholder.psd.confirm')}
          value={formValues.confirmedPassword}
          handleInputChange={handleInputChange}
        />
        )}
      {!isSignUpForm && !isResetPasswordForm && <p className={forgotPassword} onClick={() => handleForgotPassword && handleForgotPassword()}>{t('string.signIn.forgotPsd')}</p>}
      <Button.FormButton className={['formButton']} onClick={handleFormOnClick}>
        {formButtonText}
      </Button.FormButton>
    </form>
  );
};
export default Form;
