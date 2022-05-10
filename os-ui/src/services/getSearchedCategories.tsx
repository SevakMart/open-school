import { fetchDataGet } from './fetchData';

export const getSearchedCategories = async (url:string) => {
  const data = await fetchDataGet(url);
  return data;
};
