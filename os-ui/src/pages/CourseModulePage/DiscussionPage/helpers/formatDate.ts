export const formatDate = (someDate: string) => {
  const dateObj = new Date(someDate);

  const getTimezoneOffsetString = () => {
    const timezoneOffsetInMinutes = new Date().getTimezoneOffset();
    const sign = timezoneOffsetInMinutes > 0 ? '-' : '+';
    const Boolsign = timezoneOffsetInMinutes < 0;
    const hours = Math.abs(Math.floor(timezoneOffsetInMinutes / 60));
    const minutes = Math.abs(timezoneOffsetInMinutes % 60);
    return [sign, hours < 10 ? `0${hours}` : hours, minutes < 10 ? `0${minutes}` : minutes, Boolsign];
  };

  const timeZoneAffect = getTimezoneOffsetString();

  // Use the Date object's methods to extract the desired date and time components
  let date = dateObj.getUTCDate();
  const month = dateObj.getUTCMonth() + 1;
  const year = dateObj.getUTCFullYear();
  const hours = dateObj.getUTCHours();
  const minutes = dateObj.getUTCMinutes();

  // Add the GMT hours and minutes to the real hours and minutes
  let hoursWithGMT = hours + (+`${timeZoneAffect[0]}${timeZoneAffect[1]}`);
  let minutesWithGMT = minutes + (+`${timeZoneAffect[0]}${timeZoneAffect[2]}`);
  if (hoursWithGMT < 0) {
    hoursWithGMT += 24;
    date -= 1;
  }

  // Adjust the date if hours or minutes go beyond 24 or 60, respectively
  if (hoursWithGMT >= 24) {
    date += Math.floor(hoursWithGMT / 24);
    hoursWithGMT %= 24;
  }
  if (minutesWithGMT >= 60) {
    hoursWithGMT += Math.floor(minutesWithGMT / 60);
    minutesWithGMT %= 60;
    if (hoursWithGMT >= 24) {
      date += Math.floor(hoursWithGMT / 24);
      hoursWithGMT %= 24;
    }
  }
  if (+`${timeZoneAffect[2]}` === 15
    || +`${timeZoneAffect[2]}` === 30
    || +`${timeZoneAffect[2]}` === 45) {
    hoursWithGMT = timeZoneAffect[3] ? hoursWithGMT - 1 : hoursWithGMT;
  }

  // Format the components into desired string format
  const formattedDate = `${date < 10 ? `0${date}` : date}.${month < 10 ? `0${month}` : month}.${year}`;
  const formattedTime = `${Math.abs(hoursWithGMT) < 10
    ? `0${Math.abs(hoursWithGMT)}` : Math.abs(hoursWithGMT)}:${Math.abs(minutesWithGMT) < 10 ? `0${Math.abs(minutesWithGMT)}`
    : Math.abs(minutesWithGMT)}`;

  // Combine the date, time, and timezone offset components into your final formatted string
  const formattedDateTime = `${formattedDate}, ${formattedTime}`;

  return formattedDateTime;
};
