export const savePreferredCategories = async (
  url:string,
  userId:number,
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
    body: JSON.stringify({ userId, categoriesIdSet: content }),
  });
  const data = await response.json();
  return data;
};
