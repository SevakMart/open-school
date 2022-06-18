import { useState, useEffect } from 'react';
import { FiBookmark } from 'react-icons/fi';

const BookmarkIcon = (
  {
    iconSize, isBookmarked, courseId, saveCourse, deleteCourse, mentorId, saveMentor, deleteMentor,
  }:
  {iconSize:string, isBookmarked?:boolean, courseId?:number, mentorId?:number
  saveCourse?:(courseId:number)=>void,
  deleteCourse?:(courseId:number)=>void,
  saveMentor?:(mentorId:number)=>void,
  deleteMentor?:(mentorId:number)=>void
  },
) => {
  const [isClicked, setIsClicked] = useState(isBookmarked);

  const handleCourseSaving = () => {
    setIsClicked((prevState) => !prevState);
  };
  useEffect(() => {
    if (isClicked) {
      saveCourse && saveCourse(courseId!);
      saveMentor && saveMentor(mentorId!);
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
