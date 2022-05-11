import { ResetPasswordType } from '../types/ResetPasswordType';

export const sendResetPasswordRequest = async (url:string, content:ResetPasswordType) => {
  const response = await fetch(url, {
    method: 'POST',
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(content),
  });
  const { status } = response;
  const data = await response.json();
  return { data, status };
};
