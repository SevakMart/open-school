// This service is susceptible of changes until the api is ready.
import { fetchDataGet } from './fetchData';

export const getMentors = async (url:string) => {
  const data = await fetchDataGet(url);
  return data;
};
