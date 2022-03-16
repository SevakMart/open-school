// This service is susceptible of changes until the api is ready.
export const getMentors = async (url:string, page:number) => {
  const res = await fetch(url, {
    method: 'GET',
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json',
    },
  });
  const data = await res.json();
  return data;
};
