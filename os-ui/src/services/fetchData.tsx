import { ERROR_MESSAGE } from '../constants/Strings';

const handleError = () => new Response(JSON.stringify({
  errorMessage: ERROR_MESSAGE,
}));

const getQueryParams = (url: string, params: object) => {
  const searchParams = new URLSearchParams();
  (Object.keys(params) as (keyof typeof params)[]).forEach((key) => searchParams.append(key, params[key]));
  return url + searchParams.toString();
};

const request = async (url: string, method: string, body: unknown = null, params: object = {}, token = '') => {
  const urlWithParams = getQueryParams(url, params);

  const response = await (fetch(urlWithParams.toString(), {
    method,
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
    body: body === null ? null : JSON.stringify(body),
  })
    .catch(handleError));
  return response.json();
};

export const fetchDataGet = async (url: string, params: object = {}, token = '') => request(url, 'GET', null, params, token);
export const fetchDataPost = async (url: string, body: unknown, params: object, token = '') => request(url, 'POST', body, params, token);
