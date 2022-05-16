import { createUrl } from '../helpers/CreateUrl';
import { handleError } from '../helpers/HandleError';

export const request = async (url: string, method: string, params: object = {}, token = '', body:unknown = null) => {
  const mainUrl = createUrl(url, params);
  let content;
  if (body && (typeof body !== 'string')) {
    content = JSON.stringify(body);
  } else if (body && (typeof body === 'string')) {
    content = body;
  }
  const response = await (fetch(mainUrl, {
    method,
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
    body: body ? content : null,
  })
    .catch(handleError));
  return response;
};
