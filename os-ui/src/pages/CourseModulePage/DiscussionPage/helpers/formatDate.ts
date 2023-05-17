export const formatDate = (someDate: string) => {
  const dateObj = new Date(someDate);

  const getTimezoneOffsetString = () => {
    const timezoneOffsetInMinutes = new Date().getTimezoneOffset();
    const sign = timezoneOffsetInMinutes > 0 ? '-' : '+';
    const hours = Math.abs(Math.floor(timezoneOffsetInMinutes / 60));
    const minutes = Math.abs(timezoneOffsetInMinutes % 60);
    return [sign, hours < 10 ? `0${hours}` : hours, minutes < 10 ? `0${minutes}` : minutes];
  };

  const timeZoneAffect = getTimezoneOffsetString();

  // Use the Date object's methods to extract the desired date and time components
  const date = dateObj.getUTCDate();
  const month = dateObj.getUTCMonth() + 1;
  const year = dateObj.getUTCFullYear();
  const hours = dateObj.getUTCHours();
  const minutes = dateObj.getUTCMinutes();

  // Add the GMT hours and minutes to the real hours and minutes
  const hoursWithGMT = hours + +`${timeZoneAffect[0]}${timeZoneAffect[1]}`;
  const minutesWithGMT = minutes + +`${timeZoneAffect[0]}${timeZoneAffect[2]}`;

  // Format the components into desired string format
  const formattedDate = `${date < 10 ? `0${date}` : date}.${month < 10 ? `0${month}` : month}.${year}`;
  const formattedTime = `${hoursWithGMT < 10 ? `0${hoursWithGMT}` : hoursWithGMT}:${minutesWithGMT < 10 ? `0${minutesWithGMT}` : minutesWithGMT}`;

  // Combine the date, time, and timezone offset components into your final formatted string
  const formattedDateTime = `${formattedDate}, ${formattedTime}`;

  return formattedDateTime;
};
