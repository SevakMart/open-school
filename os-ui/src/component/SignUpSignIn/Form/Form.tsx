import { useContext, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import { Input } from '../../Input/Input';
import { Types } from '../../../types/types';
import { openModal } from '../../../redux/Slices/PortalOpenStatus';
import Button from '../../Button/Button';
import styles from './Form.module.scss';
import { signInContext } from '../../../contexts/Contexts';
import { PASSWORD_REQUIRED } from '../../../constants/Strings';
import { FormProps } from '../../../types/FormTypes';
import PopupCodeToVerify from '../SignIn/PopupCodeToVerify/PopupCodeToVerify';
import ResendButton from '../helpers/ResendButton/ResendButton';

const Form = ({
  isSignUpForm,
  isResetPasswordForm,
  formButtonText,
  errorFormValue,
  unAuthorizedSignInError,
  handleForm,
  resendEmail,
}: FormProps) => {
  const [errorMessage, setErrorMessage] = useState('');
  const { setSignIn } = useContext(signInContext);
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const {
    inputContent, forgotPassword, unAuthorizedSignInErrorStyle,
  } = styles;

  const initialFormValues = {
    firstName: '',
    lastName: '',
    email: '',
    psd: '',
    token: '',
    newPassword: '',
    confirmedPassword: '',
  };

  const [formValues, setFormValues] = useState(initialFormValues);

  const handleInputChange = (e:React.SyntheticEvent) => {
    setFormValues({
      ...formValues,
      [(e.target as HTMLInputElement).name]: (e.target as HTMLInputElement).value,
    });
  };

  const handleForgotPassword = () => {
    dispatch(openModal({ buttonType: Types.Button.FORGOT_PASSWORD }));
  };

  const handleFormOnClick = (e: React.SyntheticEvent) => {
    e.preventDefault();
    const trimmedPassword = isResetPasswordForm ? formValues.confirmedPassword.trim() : formValues.psd.trim();
    if (trimmedPassword === '') {
	  setErrorMessage(PASSWORD_REQUIRED);
	  return;
    }
    setErrorMessage('');
    handleForm({ ...formValues, confirmedPassword: trimmedPassword, psd: trimmedPassword });
    setSignIn(true);
  };

  const handleForgotVerification = () => {
    dispatch(openModal({ buttonType: Types.Button.FORGOT_VERIFICATION, forgotVerficationEmail: formValues.email }));
  };

  const handleSubmit = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      e.preventDefault();
      handleFormOnClick(e);
    }
  };

  return (
    <form className={inputContent}>
      {isSignUpForm && (
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
      {isResetPasswordForm && (
      <PopupCodeToVerify errorMessage={errorFormValue.tokenError} formValues={formValues} setFormValues={setFormValues} />
      )}
      {!isResetPasswordForm && (
        <Input.EmailInput
          textName="email"
          labelText={t('form.labels.email')}
          errorMessage={errorFormValue.emailError}
          placeholderText={t('form.placeholder.email')}
          value={formValues.email}
          handleInputChange={handleInputChange}
          handleEnterPress={handleSubmit}
        />
      )}
      <Input.PasswordInput
        textName={isResetPasswordForm ? 'newPassword' : 'psd'}
        labelText={
          isResetPasswordForm
            ? t('form.labels.psd.reset')
            : t('form.labels.psd.default')
        }
        errorMessage={
          isResetPasswordForm
            ? errorFormValue.newPasswordError
            : errorFormValue.psdError
        }
        placeholderText={
          isResetPasswordForm
            ? t('form.placeholder.psd.reset')
            : t('form.placeholder.psd.default')
        }
        value={isResetPasswordForm ? formValues.newPassword : formValues.psd}
        handleInputChange={handleInputChange}
        handleEnterPress={handleSubmit}
      />
      {isResetPasswordForm && (
        <Input.PasswordInput
          textName="confirmedPassword"
          labelText={t('form.labels.psd.confirm')}
          errorMessage={errorMessage || errorFormValue.confirmedPasswordError}
          placeholderText={t('form.placeholder.psd.confirm')}
          value={formValues.confirmedPassword}
          handleInputChange={handleInputChange}
          handleEnterPress={handleSubmit}
        />
      )}
      {isResetPasswordForm && (
        <ResendButton resendEmail={resendEmail} />
      )}
      {unAuthorizedSignInError ? (
        <p className={unAuthorizedSignInErrorStyle}>
          {unAuthorizedSignInError}
        </p>
      ) : null}
      {unAuthorizedSignInError === 'User is disabled' ? (
        <Button.FormButton
          className={['formButton', 'formButton__resendEmail']}
          onClick={handleForgotVerification}
        >
          {t('Resend Verification Mail')}
        </Button.FormButton>
      ) : (
        <>
          {!isSignUpForm && !isResetPasswordForm && (
            <p className={forgotPassword} onClick={handleForgotPassword}>
              {t('string.signIn.forgotPsd')}
            </p>
          )}
          <Button.FormButton
            className={['formButton']}
            onClick={handleFormOnClick}
          >
            {formButtonText}
          </Button.FormButton>
        </>
      )}
    </form>
  );
};

export default Form;
