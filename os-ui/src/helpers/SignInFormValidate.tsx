import { RegistrationFormType } from '../types/RegistartionFormType';
import {
  PASSWORD_REQUIRED, EMAIL_REQUIRED, INVALID_EMAIL_ERROR_MESSAGE, emailRegex,
} from '../constants/Strings';

export const validateSignInForm = (formValues:RegistrationFormType) => {
  const errorFormValue = { fullNameError: '', emailError: '', passwordError: '' };
  const { email, password } = formValues;

  if (email.length === 0 && password.length === 0) {
    errorFormValue.emailError = EMAIL_REQUIRED;
    errorFormValue.passwordError = PASSWORD_REQUIRED;
    return errorFormValue;
  }
  if (!email.match(emailRegex) && password.length === 0) {
    errorFormValue.emailError = INVALID_EMAIL_ERROR_MESSAGE;
    errorFormValue.passwordError = PASSWORD_REQUIRED;
    return errorFormValue;
  }
  if (email.length === 0) {
    errorFormValue.emailError = EMAIL_REQUIRED;
    return errorFormValue;
  }
  if (!email.match(emailRegex)) {
    errorFormValue.emailError = INVALID_EMAIL_ERROR_MESSAGE;
    return errorFormValue;
  }
  if (password.length === 0) {
    errorFormValue.passwordError = PASSWORD_REQUIRED;
    return errorFormValue;
  }
  return errorFormValue;
};
