export const filterSendingParams = (sendingParams:object) => {
  const filteredParams = Object.entries(sendingParams).filter((params) => params[1].length > 0);
  return Object.fromEntries(filteredParams);
};
