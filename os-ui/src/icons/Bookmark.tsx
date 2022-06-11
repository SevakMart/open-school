import { useContext, useState, useEffect } from 'react';
import { FiBookmark } from 'react-icons/fi';
import { courseBookmarkContext, userContext } from '../contexts/Contexts';
import userService from '../services/userService';

const BookmarkIcon = (
  {
    iconSize, isBookmarked, courseId, saveCourse, deleteCourse,
  }:
  {iconSize:string, isBookmarked?:boolean, courseId?:number,
  saveCourse?:(courseId:number)=>void,
  deleteCourse?:(courseId:number)=>void},
) => {
  const [isClicked, setIsClicked] = useState(isBookmarked);
  // const [bookmarked, setBookmarked] = useState(false);
  // const courseId = useContext(courseBookmarkContext);
  const { token, id } = useContext(userContext);

  const handleCourseSaving = () => {
    setIsClicked((prevState) => !prevState);
    // handleModification && handleModification(courseId);
  };
  useEffect(() => {
    if (isClicked) {
      saveCourse && saveCourse(courseId!);
    } else if (!isClicked) {
      deleteCourse && deleteCourse(courseId!);
    }
  }, [isClicked]);

  /* useEffect(() => {
    if (isClicked && !isBookmarked) {
      if (courseId) userService.saveUserPreferredCourses(courseId, token);
    } else if (!isClicked && isBookmarked) {
      if (courseId) userService.deleteUserSavedCourses(courseId, token);
    }
  }, [isClicked, isBookmarked]); */

  return (
    <FiBookmark
      onClick={handleCourseSaving}
      style={{
        fontSize: `${iconSize}`,
        cursor: 'pointer',
        color: '#5E617B',
        fill: isClicked ? 'black' : 'none',
      }}
    />
  );
};
export default BookmarkIcon;
