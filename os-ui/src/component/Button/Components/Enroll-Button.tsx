import { useDispatch } from 'react-redux';
import { enrollCourse } from '../../../redux/Slices/EnrollCourseSlice';
import { openModal } from '../../../redux/Slices/PortalOpenStatus';
import { Types } from '../../../types/types';
import styles from '../Button-Styles.module.scss';

/* eslint-disable max-len */
export const EnrollButton = ({
  children, className, userId, token, courseId,
}:
    {children:React.ReactElement, className:Array<string>, userId:number, token:string, courseId:number}) => {
  const styleNames = className.map((className:any) => styles[`${className}`]);
  const dispatch = useDispatch();
  const handleEnrollClick = () => {
    dispatch(enrollCourse({ userId, courseId, token }));
    dispatch(openModal({ buttonType: Types.Button.ENROLL_COURSE }));
  };
  return (
    <button type="button" className={styleNames.join(' ')} onClick={handleEnrollClick}>{children}</button>
  );
};
