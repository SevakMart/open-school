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
  const FullTokenResponse = response.headers.get('Authorization');
  const tokenStartingIndex = FullTokenResponse ? FullTokenResponse.indexOf(' ') : undefined;
  const token = FullTokenResponse && tokenStartingIndex
    ? FullTokenResponse.slice(tokenStartingIndex + 1) : null;
  return { ...data, status: response.status, token };
};
