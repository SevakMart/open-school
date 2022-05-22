import { ERROR_MESSAGE } from '../constants/Strings';

export const handleError = () => new Response(JSON.stringify({
  errorMessage: ERROR_MESSAGE,
}));
