import { createUrl } from '../helpers/CreateUrl';
import { handleError } from '../helpers/HandleError';

export const request = async (url: string, method: string, params: object = {}, token = '', body: unknown = null) => {
  const mainUrl = createUrl(url, params);

  const response = await (fetch(mainUrl, {
    method,
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
    body: body ? JSON.stringify(body) : null,
  })
    .catch(handleError));
  return response;
};
