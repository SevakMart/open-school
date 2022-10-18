import { FormValues } from '../component/SignUpSignIn/Form/Form';
import {
  PASSWORD_REQUIRED, EMAIL_REQUIRED, INVALID_EMAIL_ERROR_MESSAGE, emailRegex,
} from '../constants/Strings';

export const validateSignInForm = (formValues:FormValues) => {
  const errorFormValue = { fullNameError: '', emailError: '', passwordError: '' };
  const { email, psd } = formValues;

  if (email.length === 0 && psd.length === 0) {
    errorFormValue.emailError = EMAIL_REQUIRED;
    errorFormValue.passwordError = PASSWORD_REQUIRED;
    return errorFormValue;
  }
  if (!email.match(emailRegex) && psd.length === 0) {
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
  if (psd.length === 0) {
    errorFormValue.passwordError = PASSWORD_REQUIRED;
    return errorFormValue;
  }
  return errorFormValue;
};
