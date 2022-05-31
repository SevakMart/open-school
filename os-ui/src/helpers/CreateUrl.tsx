export const createUrl = (url:string, params:object) => {
  const searchParams = new URLSearchParams();
  if ((Object.keys(params) as (keyof typeof params)[]).length) {
    (Object.keys(params) as (keyof typeof params)[])
      .forEach((key) => {
        if (Array.isArray(params[key]) && (params[key] as string[]).length) {
          // return searchParams.append(key, (params[key] as string[]).join());
          (params[key] as string[]).forEach((param) => searchParams.append(key, param));
        }
        searchParams.append(key, params[key]);
      });
    return `${url}?${searchParams.toString()}`;
  }
  // if there are no params return the url
  return url;
};
