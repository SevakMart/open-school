import { EMAIL_REQUIRED, INVALID_EMAIL_ERROR_MESSAGE, emailRegex } from '../constants/Strings';

export const validateEmail = (email:string) => {
  let errorMessage = '';

  if (!email.match(emailRegex)) {
    errorMessage = INVALID_EMAIL_ERROR_MESSAGE;
  }
  if (email.length === 0) {
    errorMessage = EMAIL_REQUIRED;
  }
  return errorMessage;
};
