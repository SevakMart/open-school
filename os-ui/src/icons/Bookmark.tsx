import { useState, useEffect } from 'react';
import { FiBookmark } from 'react-icons/fi';

const BookmarkIcon = (
  {
    iconSize, isBookmarked, courseId, saveCourse, deleteCourse,
  }:
  {iconSize:string, isBookmarked?:boolean, courseId?:number,
  saveCourse?:(courseId:number)=>void,
  deleteCourse?:(courseId:number)=>void},
) => {
  const [isClicked, setIsClicked] = useState(isBookmarked);

  const handleCourseSaving = () => {
    setIsClicked((prevState) => !prevState);
  };
  useEffect(() => {
    if (isClicked) {
      saveCourse && saveCourse(courseId!);
    } else if (!isClicked && isBookmarked) {
      deleteCourse && deleteCourse(courseId!);
    }
  }, [isClicked]);

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
