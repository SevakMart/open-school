import {
  emailRegex, passwordRegex, EMAIL_REQUIRED, EMAIL_TOO_LONG, INVALID_EMAIL_ERROR_MESSAGE,
  PASSWORD_REQUIRED, INVALID_PASSWORD_ERROR_MESSAGE, FIRSTNAME_REQUIRED, LASTNAME_REQUIRED,
  FIRSTNAME_TOO_LONG, LASTNAME_TOO_LONG,
} from '../constants/Strings';
import { FormValues } from '../types/FormTypes';

export const validateSignUpForm = (formValues:FormValues) => {
  const errorFormValue = {
    firstNameError: '', lastNameError: '', emailError: '', psdError: '',
  };
  const {
    firstName, lastName, email, psd,
  } = formValues;
  if (email.length === 0) {
    errorFormValue.emailError = EMAIL_REQUIRED;
  } else if (email.length > 45) {
    errorFormValue.emailError = EMAIL_TOO_LONG;
  } else if (!email.match(emailRegex)) {
    errorFormValue.emailError = INVALID_EMAIL_ERROR_MESSAGE;
  }

  if (psd.length === 0) {
    errorFormValue.psdError = PASSWORD_REQUIRED;
  } else if (!psd.match(passwordRegex)) {
    errorFormValue.psdError = INVALID_PASSWORD_ERROR_MESSAGE;
  }

  if (firstName.length === 0 || firstName.trim().length === 0) {
    errorFormValue.firstNameError = FIRSTNAME_REQUIRED;
  } else if (firstName.length > 45) {
    errorFormValue.firstNameError = FIRSTNAME_TOO_LONG;
  }

  if (lastName.length === 0 || lastName.trim().length === 0) {
    errorFormValue.lastNameError = LASTNAME_REQUIRED;
  } else if (lastName.length > 45) {
    errorFormValue.lastNameError = LASTNAME_TOO_LONG;
  }
  return errorFormValue;
};
