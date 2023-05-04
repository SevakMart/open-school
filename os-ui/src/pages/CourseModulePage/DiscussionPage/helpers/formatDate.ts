export const formatDate = (someDate: string) => {
  const dateObj = new Date(someDate);
  // Use the Date object's methods to extract the desired date and time components
  const date = dateObj.getUTCDate();
  const month = dateObj.getUTCMonth() + 1;
  const year = dateObj.getUTCFullYear();
  const hours = dateObj.getUTCHours();
  const minutes = dateObj.getUTCMinutes();

  // Get the timezone offset in hours from the timestamp
  const offsetInMinutes = dateObj.getTimezoneOffset();
  const offsetInHours = Math.abs(offsetInMinutes / 60);
  const sign = offsetInMinutes > 0 ? '-' : '+';
  const offsetFormatted = `GMT${sign}${offsetInHours}`;

  // Format the components into desired string format
  const formattedDate = `${date < 10 ? `0${date}` : date}.${month < 10 ? `0${month}` : month}.${year}`;
  const formattedTime = `${hours < 10 ? `0${hours}` : hours}:${minutes < 10 ? `0${minutes}` : minutes}`;

  // Combine the date, time, and timezone offset components into your final formatted string
  const formattedDateTime = `${formattedDate}, ${formattedTime} (${offsetFormatted})`;

  return formattedDateTime;
};
