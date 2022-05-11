import { ERROR_MESSAGE } from '../constants/Strings';

const handleError = () => new Response(JSON.stringify({
  errorMessage: ERROR_MESSAGE,
}));

const getQueryParams = (url: string, params: object) => {
  const searchParams = new URLSearchParams();
  (Object.keys(params) as (keyof typeof params)[])
    .forEach((key) => searchParams.append(key, params[key]));
  return `${url}?${searchParams.toString()}`;
};

const request = async (url: string, method: string, params: object = {}, token = '', body: unknown = null) => {
  const urlWithParams = getQueryParams(url, params);

  const response = await (fetch(urlWithParams.toString(), {
    method,
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
    body: body ? JSON.stringify(body) : null,
  })
    .catch(handleError));
  return response.json();
};

export const fetchDataGet = async (url: string, params: object = {}, token = '') => request(url, 'GET', params, token);
export const fetchDataPost = async (url: string, body: unknown, params: object, token = '') => request(url, 'POST', params, token, body);
