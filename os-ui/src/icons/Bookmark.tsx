import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import bookMark from '../assets/svg/bookMark.svg';
import { Types } from '../types/types';
import { openModal } from '../redux/Slices/PortalOpenStatus';
import { RootState } from '../redux/Store';
import { SuggestedCourseType } from '../types/CourseTypes';
import { MentorType } from '../types/MentorType';

const BookmarkIcon = ({
  iconSize,
  courseId,
  mentorId,
  courseTitle,
  mentorName,
  isHomepageNotSignedInMentor,
  saveCourse,
  deleteCourse,
  saveMentor,
  deleteMentor,
  isCourseSummaryBookmarkIcon,
}: {
  iconSize: string;
  courseId?: number;
  mentorId?: number;
  courseTitle?: string;
  mentorName?: string;
  isHomepageNotSignedInMentor?: boolean;
  saveCourse?: (courseTitle: string, courseId: number) => void;
  deleteCourse?: (courseTitle: string, courseId: number) => void;
  saveMentor?: (mentorName: string, mentorId: number) => void;
  deleteMentor?: (mentorName: string, mentorId: number) => void;
  isCourseSummaryBookmarkIcon?: boolean;
}) => {
  const [isChecked, setIsChecked] = useState(false);
  const dispatch = useDispatch();
  const savedCourses = useSelector<RootState, SuggestedCourseType[]>((state) => state.savedCourse.entity);
  const savedMentors = useSelector<RootState, MentorType[]>((state) => state.savedMentors.entity);

  useEffect(() => {
    if (courseId && savedCourses.some((course) => course.id === courseId)) {
      setIsChecked(true);
    } else if (mentorId && savedMentors.some((mentor) => mentor.id === mentorId)) {
      setIsChecked(true);
    }
  }, [courseId, mentorId, savedCourses, savedMentors]);

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
    }
    setIsChecked((prevState) => !prevState);
  };

  const handleMentorBookmark = () => {
    dispatch(openModal({ buttonType: Types.Button.SIGN_IN, isRequestForMentorsPage: true }));
  };

  return (
    <img
      src={bookMark}
      alt="bookmark"
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
