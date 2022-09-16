import { useContext } from 'react';
import { FiBookmark } from 'react-icons/fi';
import userService from '../services/userService';
import { useCheck } from '../custom-hooks/useCheck';
import { userContext } from '../contexts/Contexts';

/* eslint-disable max-len */

const BookmarkIcon = ({
  iconSize, courseId, mentorId, courseTitle,
}:
  {iconSize:string, courseId?:number, mentorId?:number, courseTitle?:string}) => {
  const { token, id: userId } = useContext(userContext);
  const [isChecked, handleChecking] = useCheck(courseTitle!, courseId);

  const handleSaving = () => {
    if (!isChecked) {
      if (courseId) {
        userService.saveUserPreferredCourses(userId, courseId, token);
      } else if (mentorId) {
        userService.saveUserMentor(userId, mentorId, token);
      }
    } else if (isChecked) {
      if (courseId) {
        userService.deleteUserSavedCourses(userId, courseId, token);
      } else if (mentorId) {
        userService.deleteUserSavedMentor(userId, mentorId, token);
      }
    }handleChecking();
  };

  return (
    <FiBookmark
      onClick={handleSaving}
      style={{
        fontSize: `${iconSize}`,
        cursor: 'pointer',
        fill: isChecked ? 'white' : 'none',
      }}
    />
  );
};
export default BookmarkIcon;
