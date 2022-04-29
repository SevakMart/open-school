import { FiBookmark } from 'react-icons/fi';

const BookmarkIcon = ({ iconSize }:{iconSize:string}) => (
  <FiBookmark style={{ fontSize: `${iconSize}`, cursor: 'pointer', color: '#5E617B' }} />
);
export default BookmarkIcon;
