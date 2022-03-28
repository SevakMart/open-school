import { RegistrationFormType } from '../types/RegistartionFormType';

export const register = (url:string, content:RegistrationFormType) => {
  fetch(url, {
    method: 'POST',
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(content),
  });
};
