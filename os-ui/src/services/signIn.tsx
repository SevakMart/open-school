import { RegistrationFormType } from '../types/RegistartionFormType';
import { SignedInUser, NotSignedInUser } from '../types/UserType';

export const signIn = async (url:string, content:Omit<RegistrationFormType, 'firstName'>) => {
  const serverResponse:any = { data: {}, status: 0 };
  const response = await fetch(url, {
    method: 'POST',
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(content),
  });
  serverResponse.status = response.status;
  const data = await response.json();
  serverResponse.data = { ...data };
  console.log(serverResponse);
  return serverResponse;
};
