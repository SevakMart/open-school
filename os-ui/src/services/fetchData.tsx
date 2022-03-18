import { ERROR_MESSAGE } from '../constants/Strings';

const handleError = () => {
  const fetchedResponse = new Response(JSON.stringify({
    errorMessage: ERROR_MESSAGE,
  }));
  return fetchedResponse;
};

export const fetchData = async (url:string) => {
  const response = await (fetch(url, {
    method: 'GET',
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json',
    },
  }).catch(handleError));
  const data = await response.json();
  return data;
};
