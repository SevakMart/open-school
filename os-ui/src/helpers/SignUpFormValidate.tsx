import { RegistrationFormType } from '../types/RegistartionFormType';
import {
  fullNameRegex, emailRegex, passwordRegex, INVALID_EMAIL_ERROR_MESSAGE,
  INVALID_FULL_NAME_ERROR_MESSAGE, INVALID_PASSWORD_ERROR_MESSAGE, EMAIL_TOO_SHORT,
  FULL_NAME_TOO_SHORT, PASSWORD_TOO_SHORT,
} from '../constants/Strings';

export const validateSignUpForm = (formValues:RegistrationFormType) => {
  const errorFormValue = { fullNameError: '', emailError: '', passwordError: '' };
  const { firstName, email, password } = formValues;
  if (firstName.length < 8 && email.length < 8 && password.length < 8) {
    errorFormValue.fullNameError = FULL_NAME_TOO_SHORT;
    errorFormValue.emailError = EMAIL_TOO_SHORT;
    errorFormValue.passwordError = PASSWORD_TOO_SHORT;
    return errorFormValue;
  }

  if (firstName.length < 8 && email.length < 8 && password.match(passwordRegex)) {
    errorFormValue.fullNameError = FULL_NAME_TOO_SHORT;
    errorFormValue.emailError = EMAIL_TOO_SHORT;
    return errorFormValue;
  }
  if (firstName.length < 8 && email.length < 8 && !password.match(passwordRegex)) {
    errorFormValue.fullNameError = FULL_NAME_TOO_SHORT;
    errorFormValue.emailError = EMAIL_TOO_SHORT;
    errorFormValue.passwordError = INVALID_PASSWORD_ERROR_MESSAGE;
    return errorFormValue;
  }
  if (firstName.length < 8 && password.length < 8 && !email.match(emailRegex)) {
    errorFormValue.fullNameError = FULL_NAME_TOO_SHORT;
    errorFormValue.emailError = INVALID_EMAIL_ERROR_MESSAGE;
    errorFormValue.passwordError = PASSWORD_TOO_SHORT;
    return errorFormValue;
  }
  if (email.length < 8 && password.length < 8 && !firstName.match(fullNameRegex)) {
    errorFormValue.fullNameError = INVALID_FULL_NAME_ERROR_MESSAGE;
    errorFormValue.emailError = EMAIL_TOO_SHORT;
    errorFormValue.passwordError = PASSWORD_TOO_SHORT;
    return errorFormValue;
  }

  if (firstName.length < 8 && email.match(emailRegex) && password.match(passwordRegex)) {
    errorFormValue.fullNameError = FULL_NAME_TOO_SHORT;
    return errorFormValue;
  }
  if (firstName.length < 8 && !email.match(emailRegex) && password.match(passwordRegex)) {
    errorFormValue.fullNameError = FULL_NAME_TOO_SHORT;
    errorFormValue.emailError = INVALID_EMAIL_ERROR_MESSAGE;
    return errorFormValue;
  }
  if (firstName.length < 8 && email.match(emailRegex) && !password.match(passwordRegex)) {
    errorFormValue.fullNameError = FULL_NAME_TOO_SHORT;
    errorFormValue.passwordError = INVALID_PASSWORD_ERROR_MESSAGE;
    return errorFormValue;
  }
  if (firstName.length < 8 && !email.match(emailRegex) && !password.match(passwordRegex)) {
    errorFormValue.fullNameError = FULL_NAME_TOO_SHORT;
    errorFormValue.emailError = INVALID_EMAIL_ERROR_MESSAGE;
    errorFormValue.passwordError = INVALID_PASSWORD_ERROR_MESSAGE;
    return errorFormValue;
  }
  if (email.length < 8 && firstName.match(fullNameRegex) && password.match(passwordRegex)) {
    errorFormValue.emailError = EMAIL_TOO_SHORT;
    return errorFormValue;
  }
  if (email.length < 8 && !firstName.match(fullNameRegex) && password.match(passwordRegex)) {
    errorFormValue.fullNameError = INVALID_FULL_NAME_ERROR_MESSAGE;
    errorFormValue.emailError = EMAIL_TOO_SHORT;
    return errorFormValue;
  }
  if (email.length < 8 && firstName.match(fullNameRegex) && !password.match(passwordRegex)) {
    errorFormValue.emailError = EMAIL_TOO_SHORT;
    errorFormValue.passwordError = INVALID_PASSWORD_ERROR_MESSAGE;
    return errorFormValue;
  }
  if (email.length < 8 && !firstName.match(fullNameRegex) && !password.match(passwordRegex)) {
    errorFormValue.fullNameError = INVALID_FULL_NAME_ERROR_MESSAGE;
    errorFormValue.emailError = EMAIL_TOO_SHORT;
    errorFormValue.passwordError = INVALID_PASSWORD_ERROR_MESSAGE;
    return errorFormValue;
  }
  if (password.length < 8 && firstName.match(fullNameRegex) && email.match(emailRegex)) {
    errorFormValue.passwordError = PASSWORD_TOO_SHORT;
    return errorFormValue;
  }
  if (password.length < 8 && !firstName.match(fullNameRegex) && email.match(emailRegex)) {
    errorFormValue.fullNameError = INVALID_FULL_NAME_ERROR_MESSAGE;
    errorFormValue.passwordError = PASSWORD_TOO_SHORT;
    return errorFormValue;
  }
  if (password.length < 8 && firstName.match(fullNameRegex) && !email.match(emailRegex)) {
    errorFormValue.emailError = INVALID_EMAIL_ERROR_MESSAGE;
    errorFormValue.passwordError = PASSWORD_TOO_SHORT;
    return errorFormValue;
  }
  if (password.length < 8 && !firstName.match(fullNameRegex) && !email.match(emailRegex)) {
    errorFormValue.fullNameError = INVALID_FULL_NAME_ERROR_MESSAGE;
    errorFormValue.emailError = INVALID_EMAIL_ERROR_MESSAGE;
    errorFormValue.passwordError = PASSWORD_TOO_SHORT;
    return errorFormValue;
  }
  if (!firstName.match(fullNameRegex) && !email.match(emailRegex)
  && !password.match(passwordRegex)) {
    errorFormValue.fullNameError = INVALID_FULL_NAME_ERROR_MESSAGE;
    errorFormValue.emailError = INVALID_EMAIL_ERROR_MESSAGE;
    errorFormValue.passwordError = INVALID_PASSWORD_ERROR_MESSAGE;
    return errorFormValue;
  }

  if (!firstName.match(fullNameRegex) && !email.match(emailRegex)
  && password.match(passwordRegex)) {
    errorFormValue.fullNameError = INVALID_FULL_NAME_ERROR_MESSAGE;
    errorFormValue.emailError = INVALID_EMAIL_ERROR_MESSAGE;
    return errorFormValue;
  }
  if (firstName.match(fullNameRegex) && !email.match(emailRegex) && password.match(passwordRegex)) {
    errorFormValue.emailError = INVALID_EMAIL_ERROR_MESSAGE;
    return errorFormValue;
  }
  if (firstName.match(fullNameRegex) && email.match(emailRegex) && !password.match(passwordRegex)) {
    errorFormValue.passwordError = INVALID_PASSWORD_ERROR_MESSAGE;
    return errorFormValue;
  }
  if (!firstName.match(fullNameRegex) && email.match(emailRegex)
  && !password.match(passwordRegex)) {
    errorFormValue.fullNameError = INVALID_FULL_NAME_ERROR_MESSAGE;
    errorFormValue.passwordError = INVALID_PASSWORD_ERROR_MESSAGE;
    return errorFormValue;
  }
  if (firstName.match(fullNameRegex) && !email.match(emailRegex)
  && !password.match(passwordRegex)) {
    errorFormValue.emailError = INVALID_EMAIL_ERROR_MESSAGE;
    errorFormValue.passwordError = INVALID_PASSWORD_ERROR_MESSAGE;
    return errorFormValue;
  }
  if (!firstName.match(fullNameRegex) && email.match(emailRegex) && password.match(passwordRegex)) {
    errorFormValue.fullNameError = INVALID_FULL_NAME_ERROR_MESSAGE;
    return errorFormValue;
  }
  if (firstName.match(fullNameRegex) && email.match(emailRegex) && password.match(passwordRegex)) {
    return errorFormValue;
  }
  return errorFormValue;
};