export const savePreferredCategories = async (
  url:string,
  token:string,
  content:Array<number>,
) => {
  const response = await fetch(url, {
    method: 'POST',
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(content),
  });
  const data = await response.json();
  return data;
};
