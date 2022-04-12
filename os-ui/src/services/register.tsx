import { RegistrationFormType } from '../types/RegistartionFormType';

export const register = async (url:string, content:RegistrationFormType) => {
  const response = await fetch(url, {
    method: 'POST',
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(content),
  });
  const data = await response.json();
  return data;
};
