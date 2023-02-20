import { useDispatch } from 'react-redux';
import { enrollCourse } from '../../../redux/Slices/EnrollCourseSlice';
import { openModal } from '../../../redux/Slices/PortalOpenStatus';
import { Types } from '../../../types/types';
import styles from '../Button-Styles.module.scss';

type EnrollButtonProps = {
  children: React.ReactElement;
  className: Array<string>;
  userId: number;
  token: string;
  courseId: number;
  onEnroll?: () => void;
  disabled?: boolean;
};

export const EnrollButton = ({
  children,
  className,
  userId,
  token,
  courseId,
  onEnroll,
  disabled = false,
}: EnrollButtonProps) => {
  const styleNames = className.map((className: any) => styles[`${className}`]);
  const dispatch = useDispatch();

  const handleEnrollClick = () => {
    if (onEnroll) {
      onEnroll();
    }

    dispatch(enrollCourse({ userId, courseId, token }));
    dispatch(openModal({ buttonType: Types.Button.ENROLL_COURSE }));
  };

  return (
    <button
      type="button"
      className={styleNames.join(' ')}
      onClick={handleEnrollClick}
      disabled={disabled}
    >
      {children}
    </button>
  );
};
