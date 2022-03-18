import { fetchData } from './fetchData';

export const getCategories = async (url:string) => {
  const data = await fetchData(url);
  return data;
};
