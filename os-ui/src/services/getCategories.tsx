import { fetchDataGet } from './fetchData';

export const getCategories = async (url:string) => {
  const data = await fetchDataGet(url);
  return data;
};
