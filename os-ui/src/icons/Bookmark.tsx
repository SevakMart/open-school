import { useState } from 'react';
import { useLocation } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { FiBookmark } from 'react-icons/fi';
import { Types } from '../types/types';
import { openModal } from '../redux/Slices/PortalOpenStatus';

/* eslint-disable max-len */

const BookmarkIcon = ({
  iconSize, courseId, mentorId, courseTitle, mentorName, isHomepageNotSignedInMentor,
  saveCourse, deleteCourse, saveMentor, deleteMentor, isCourseSummaryBookmarkIcon,
}:
  {iconSize:string, courseId?:number, mentorId?:number, courseTitle?:string, mentorName?:string, isHomepageNotSignedInMentor?:boolean,
    saveCourse?:(courseTitle:string, courseId:number)=>void, deleteCourse?:(courseTitle:string, courseId:number)=>void, saveMentor?:(mentorName:string, mentorId:number)=>void,
    deleteMentor?:(mentorName:string, mentorId:number)=>void, isCourseSummaryBookmarkIcon?:boolean
  }) => {
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const [isChecked, setIsChecked] = useState(params.has(courseTitle!) || params.has(mentorName!) || (location.state as {courseIsSaved:boolean})?.courseIsSaved);
  const dispatch = useDispatch();

  const handleSaving = () => {
    if (!isChecked) {
      if (courseId) {
        saveCourse && saveCourse(courseTitle!, courseId);
      } else if (mentorId) {
        saveMentor && saveMentor(mentorName!, mentorId);
      }
    } else if (isChecked) {
      if (courseId) {
        deleteCourse && deleteCourse(courseTitle!, courseId);
      } else if (mentorId) {
        deleteMentor && deleteMentor(mentorName!, mentorId);
      }
    }setIsChecked((prevState) => !prevState);
  };

  const handleMentorBookmark = () => {
    dispatch(openModal({ buttonType: Types.Button.SIGN_IN, isRequestForMentorsPage: true }));
  };

  return (
    <FiBookmark
      onClick={isHomepageNotSignedInMentor ? handleMentorBookmark : handleSaving}
      style={{
        fontSize: `${iconSize}`,
        cursor: 'pointer',
        fill: isChecked && isCourseSummaryBookmarkIcon ? 'black' : isChecked ? 'white' : 'none',
      }}
    />
  );
};
export default BookmarkIcon;
