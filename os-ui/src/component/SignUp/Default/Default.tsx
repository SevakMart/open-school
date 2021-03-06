import { useState, useEffect, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import { addLoggedInUser } from '../../../redux/Slices/loginUserSlice';
import { validateSignUpForm } from '../../../helpers/SignUpFormValidate';
import { RegistrationFormType } from '../../../types/RegistartionFormType';
import authService from '../../../services/authService';
import VisibileIcon from '../../../icons/Visibility';
import HiddenIcon from '../../../icons/Hidden';
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

  useEffect(() => {
    if (isVisible) {
      (passwordInputRef.current as HTMLInputElement).type = 'text';
    } else (passwordInputRef.current as HTMLInputElement).type = 'password';
  }, [isVisible]);

  return (
    <form className={inputContent}>
      <div>
        <label htmlFor="firstName">
          {t('form.labels.name')}
          <span style={{ color: 'red' }}>*</span>
        </label>
        <input
          id="firstName"
          type="text"
          value={formValues.firstName}
          name="firstName"
          placeholder={t('form.placeholder.name')}
          data-testid="firstNameInput"
          onChange={handleInputChange}
          required
        />
        {errorFormValue.firstNameError
          ? <h4 data-testid="firstnameErrorField" className={errorField}>{errorFormValue.firstNameError}</h4>
          : null}
      </div>
      <div>
        <label htmlFor="lastName">
          {t('form.labels.surname')}
          <span style={{ color: 'red' }}>*</span>
        </label>
        <input
          id="lastName"
          type="text"
          value={formValues.lastName}
          name="lastName"
          placeholder={t('form.placeholder.surname')}
          data-testid="lastNameInput"
          onChange={handleInputChange}
          required
        />
        {errorFormValue.lastNameError
          ? <h4 data-testid="lastnameErrorField" className={errorField}>{errorFormValue.lastNameError}</h4>
          : null}
      </div>
      <div>
        <label htmlFor="email">
          {t('form.labels.email')}
          <span style={{ color: 'red' }}>*</span>
        </label>
        <input
          id="email"
          type="email"
          value={formValues.email}
          name="email"
          placeholder={t('form.placeholder.email')}
          data-testid="emailInput"
          onChange={handleInputChange}
          required
        />
        {errorFormValue.emailError
          ? <h4 data-testid="emailErrorField" className={errorField}>{errorFormValue.emailError}</h4>
          : null}
      </div>
      <div>
        <label htmlFor="psd">
          {t('form.labels.psd.default')}
          <span style={{ color: 'red' }}>*</span>
        </label>
        <input
          id="psd"
          type="password"
          ref={passwordInputRef}
          value={formValues.psd}
          name="psd"
          placeholder={t('form.placeholder.psd.default')}
          data-testid="psdInput"
          onChange={handleInputChange}
          required
        />
        {errorFormValue.psdError
          ? <h4 data-testid="psdErrorField" className={errorField}>{errorFormValue.psdError}</h4>
          : null}
        {isVisible
          ? <VisibileIcon makeInvisible={handlePasswordVisibility} />
          : <HiddenIcon makeVisible={handlePasswordVisibility} />}
      </div>
      <button type="button" data-testid="signUpButton" onClick={handleSubmitForm}>{t('button.homePage.signUp')}</button>
    </form>
  );
};
export default SignUpDefault;
