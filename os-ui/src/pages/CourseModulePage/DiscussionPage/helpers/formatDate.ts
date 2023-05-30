import { DateTime } from 'luxon';

export const formatDate = (dateString: string) => {
  const dateTime = DateTime.fromISO(dateString, { zone: 'utc' }).toLocal();
  const formattedDate = dateTime.toFormat('dd.MM.yyyy, HH:mm');
  return formattedDate;
};
