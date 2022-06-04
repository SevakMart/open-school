import { useContext, useState, useEffect } from 'react';
import { FiBookmark } from 'react-icons/fi';
import { courseBookmarkContext, userContext } from '../contexts/Contexts';
import userService from '../services/userService';

const BookmarkIcon = ({ iconSize, isBookmarked }:
  {iconSize:string, isBookmarked:boolean|undefined}) => {
  const [isClicked, setIsClicked] = useState(false);
  const courseId = useContext(courseBookmarkContext);
  const { token, id } = useContext(userContext);

  const handleCourseSaving = () => {
    setIsClicked((prevState) => !prevState);
  };

  useEffect(() => {
    if (isClicked) {
      userService.saveUserPreferredCourses(id, courseId, token);
    }
  }, [isClicked]);

  return (
    <FiBookmark
      onClick={handleCourseSaving}
      style={{
        fontSize: `${iconSize}`,
        cursor: 'pointer',
        color: '#5E617B',
        fill: isClicked || isBookmarked ? 'black' : 'none',
      }}
    />
  );
};
export default BookmarkIcon;
