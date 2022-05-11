export const transformTime = (time:number) => {
  const s = time / 1000;
  const hour = Math.floor(s / 3600);
  const minutes = Math.floor((s - hour * 3600) / 60);
  return `${hour}h ${minutes}m`;
};
