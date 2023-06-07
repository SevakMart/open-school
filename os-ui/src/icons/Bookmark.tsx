import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { FiBookmark } from 'react-icons/fi';
import { Types } from '../types/types';
import { openModal } from '../redux/Slices/PortalOpenStatus';
import { RootState } from '../redux/Store';
import { SuggestedCourseType } from '../types/CourseTypes';

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

  useEffect(() => {
    if (courseId && savedCourses.some((course) => course.id === courseId)) {
      setIsChecked(true);
    }
  }, [courseId, savedCourses]);

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
