// This service is susceptible of changes until the api is ready.
import { fetchData } from './fetchData';

export const getMentors = async (url:string) => {
  const data = await fetchData(url);
  return data;
};
