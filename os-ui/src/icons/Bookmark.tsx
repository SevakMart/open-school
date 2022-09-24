import { useContext } from 'react';
import { useDispatch } from 'react-redux';
import { FiBookmark } from 'react-icons/fi';
import userService from '../services/userService';
import { useCheck } from '../custom-hooks/useCheck';
import { Types } from '../types/types';
import { openModal } from '../redux/Slices/PortalOpenStatus';
import { deleteUserSavedCourse } from '../redux/Slices/DeleteUserSavedCourse';
import { deleteUserSavedMentor } from '../redux/Slices/DeleteSavedMentor';
import { userContext } from '../contexts/Contexts';

/* eslint-disable max-len */

const BookmarkIcon = ({
  iconSize, courseId, mentorId, courseTitle, mentorName, isHomepageNotSignedInMentor,
}:
  {iconSize:string, courseId?:number, mentorId?:number, courseTitle?:string, mentorName?:string, isHomepageNotSignedInMentor?:boolean}) => {
  const { token, id: userId } = useContext(userContext);
  const [isChecked, handleChecking] = useCheck(mentorName! || courseTitle!, mentorId || courseId);
  const dispatch = useDispatch();

  const handleSaving = () => {
    if (!isChecked) {
      if (courseId) {
        userService.saveUserPreferredCourses(userId, courseId, token);
      } else if (mentorId) {
        userService.saveUserMentor(userId, mentorId, token);
      }
    } else if (isChecked) {
      if (courseId) {
        dispatch(deleteUserSavedCourse({ userId, courseId, token }));
      } else if (mentorId) {
        dispatch(deleteUserSavedMentor({ userId, mentorId, token }));
      }
    }handleChecking();
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
        fill: isChecked ? 'white' : 'none',
      }}
    />
  );
};
export default BookmarkIcon;
