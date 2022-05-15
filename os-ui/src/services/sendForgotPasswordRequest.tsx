export const sendForgotPasswordRequest = async (url:string, content:string) => {
  const response = await fetch(url, {
    method: 'POST',
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json',
    },
    body: content,
  });
  const { status } = response;
  const data = await response.json();
  return { data, status };
};
