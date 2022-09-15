import { useState, useEffect, useContext } from 'react';
import { FiBookmark } from 'react-icons/fi';
import userService from '../services/userService';
import { userContext } from '../contexts/Contexts';

const BookmarkIcon = ({
  iconSize, isBookmarked, courseId, mentorId,
}:
  {iconSize:string, isBookmarked?:boolean, courseId?:number, mentorId?:number}) => {
  const [isClicked, setIsClicked] = useState(isBookmarked);
  const { token, id: userId } = useContext(userContext);

  const handleCourseSaving = () => {
    setIsClicked((prevState) => !prevState);
  };

  useEffect(() => {
    if (isClicked) {
      if (courseId) {
        userService.saveUserPreferredCourses(userId, courseId, token);
      } else if (mentorId) {
        userService.saveUserMentor(userId, mentorId, token);
      }
    } else if (!isClicked) {
      if (courseId) {
        userService.deleteUserSavedCourses(userId, courseId, token);
      } else if (mentorId) {
        userService.deleteUserSavedMentor(userId, mentorId, token);
      }
    }
  }, [isClicked]);

  return (
    <FiBookmark
      onClick={handleCourseSaving}
      style={{
        fontSize: `${iconSize}`,
        cursor: 'pointer',
        fill: isClicked ? 'white' : 'none',
      }}
    />
  );
};
export default BookmarkIcon;
