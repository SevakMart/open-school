import { RegistrationFormType } from '../types/RegistartionFormType';

export const signIn = async (url:string, content:Omit<RegistrationFormType, 'firstName'>) => {
  const response = await fetch(url, {
    method: 'POST',
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(content),
  });
  const data = await response.json();
  return { ...data, status: response.status };
};
