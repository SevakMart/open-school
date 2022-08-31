const currentLocation = window.location;
export const params = new URLSearchParams(currentLocation.search);

export const addSearchQueries = (key:string, value:string) => {
  params.append(key, value);
  return params;
};
export const getAllValuesForASpecificQuery = (key:string) => params.getAll(key);
export const deleteSpecificSearchQuery = (key:string) => {
  params.delete(key);
  return params;
};
export const getValueFromQuery = (key:string) => params.get(key);
