import { fetchData } from './fetchData';

export const getSuggestedCourses = async (url:string) => {
  const data = await fetchData(url);
  return data;
};
