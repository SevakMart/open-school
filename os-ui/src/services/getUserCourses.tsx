import { fetchData } from './fetchData';

export const getUserCourses = async (url:string) => {
  const data = await fetchData(url);
  return data;
};
