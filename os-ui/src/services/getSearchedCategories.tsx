import { fetchData } from './fetchData';

export const getSearchedCategories = async (url:string) => {
  const data = await fetchData(url);
  return data;
};
