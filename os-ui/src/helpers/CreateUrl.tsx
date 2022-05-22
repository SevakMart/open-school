export const createUrl = (url:string, params:object) => {
  const searchParams = new URLSearchParams();
  if ((Object.keys(params) as (keyof typeof params)[]).length) {
    (Object.keys(params) as (keyof typeof params)[])
      .forEach((key) => searchParams.append(key, params[key]));
    return `${url}?${searchParams.toString()}`;
  }
  // if there are no params return the url
  return url;
};
