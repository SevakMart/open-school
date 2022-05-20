import { ResetPasswordType } from '../types/ResetPasswordType';
import {
  passwordRegex, tokenRegex, INVALID_PASSWORD_ERROR_MESSAGE, INVALID_TOKEN,
  TOKEN_REQUIRED, PASSWORD_REQUIRED, PASSWORDS_MISMATCH,
} from '../constants/Strings';

export const validateResetPasswordForm = (formValues:ResetPasswordType) => {
  const errorFormValue = { tokenError: '', newPasswordError: '', confirmedPasswordError: '' };
  const { token, newPassword, confirmedPassword } = formValues;
  if (!token.match(tokenRegex)) {
    errorFormValue.tokenError = INVALID_TOKEN;
  }
  if (token.length === 0) {
    errorFormValue.tokenError = TOKEN_REQUIRED;
  }
  if (!newPassword.match(passwordRegex)) {
    errorFormValue.newPasswordError = INVALID_PASSWORD_ERROR_MESSAGE;
  }
  if (newPassword.length === 0) {
    errorFormValue.newPasswordError = PASSWORD_REQUIRED;
  }
  if (confirmedPassword !== newPassword) {
    errorFormValue.confirmedPasswordError = PASSWORDS_MISMATCH;
  }
  if (confirmedPassword.length === 0) {
    errorFormValue.confirmedPasswordError = PASSWORD_REQUIRED;
  }
  return errorFormValue;
};
