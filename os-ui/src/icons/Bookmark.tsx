import { useContext, useState, useEffect } from 'react';
import { FiBookmark } from 'react-icons/fi';
import { courseBookmarkContext, userContext } from '../contexts/Contexts';
import userService from '../services/userService';

const BookmarkIcon = ({ iconSize }:{iconSize:string}) => {
  const [isClicked, setIsClicked] = useState(false);
  const courseId = useContext(courseBookmarkContext);
  const { token, userId } = useContext(userContext);

  const handleCourseSaving = () => {
    setIsClicked((prevState) => !prevState);
  };

  useEffect(() => {
    if (isClicked) {
      userService.saveUserPreferredCourses(userId, courseId, token);
    }
  }, [isClicked]);

  return (
    <FiBookmark onClick={handleCourseSaving} style={{ fontSize: `${iconSize}`, cursor: 'pointer', color: '#5E617B' }} />
  );
};
export default BookmarkIcon;
