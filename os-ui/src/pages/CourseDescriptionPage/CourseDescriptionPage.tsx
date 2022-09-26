import { useEffect, useState, useMemo } from 'react';
import { useLocation, useParams, useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';

import { RootState } from '../../redux/Store';
import courseService from '../../services/courseService';
import userService from '../../services/userService';
import NavbarOnSignIn from '../../component/Navbar-Component/NavbarOnSignIn/NavbarOnSignIn';
import CourseMainContent from './Subcomponents/CourseMainContent/CourseMainContent';
import CourseSummary from './Subcomponents/CourseSummary/CourseSummary';
import Modal from '../../component/Modal/Modal';
import ModalMessageComponent from './Subcomponents/CourseSummary/Subcomponent/ModalMessageComponent/ModalMessageComponent';
import { CourseDescriptionType } from '../../types/CourseDescriptionType';
import styles from './CourseDescriptionPage.module.scss';

const CourseDescriptionPage = () => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const [courseInfo, setCourseInfo] = useState({});
  const [errorMessage, setErrorMessage] = useState('');
  const [enrollmentErrorMessage, setEnrollmentErrorMessage] = useState('');
  const [showEnrollmentMessage, setShowEnrollmentMessage] = useState(false);
  const [isEnrolled, setIsEnrolled] = useState(false);
  const location = useLocation();
  const navigate = useNavigate();
  const { t } = useTranslation();
  const { courseId } = useParams();
  const params = new URLSearchParams(location.search);
  const idAndToken = useMemo(() => ({
    token: (userInfo as any).token,
    id: (userInfo as any).id,
  }), []);
  const {
    title, description, goal, modules, mentorDto, rating, enrolled, level, language, duration,
  } = courseInfo as CourseDescriptionType;
  const { mainContent, navAndMainTitle } = styles;

  const enrollInCourse = () => {
    userService.enrollCourse(idAndToken.id, Number(courseId), idAndToken.token)
      .then(() => setShowEnrollmentMessage(true))
      .catch(() => setEnrollmentErrorMessage(t('messages.error')));
  };

  const removeModalMessageAfterCourseEnrollment = () => {
    params.set('enrolled', 'true');
    setIsEnrolled(true);
    setShowEnrollmentMessage(false);
    navigate(`${location.pathname}?${params}`);
  };

  useEffect(() => {
    if (params.has('enrolled')) setIsEnrolled(true);
    courseService.requestCourseDescription(Number(courseId), {}, idAndToken.token)
      .then((courseDescriptionData) => setCourseInfo({ ...courseDescriptionData }))
      .catch(() => setErrorMessage(t('messages.error')));
  }, []);
  return (
    <>
      <div className={navAndMainTitle}>
        <NavbarOnSignIn />
        <h1>{title}</h1>
      </div>
      {!errorMessage && Object.keys(courseInfo).length ? (
        <div className={mainContent}>
          <CourseMainContent
            description={description}
            goal={goal}
            mentorDto={mentorDto}
            modules={modules}
          />
          <CourseSummary
            rating={rating}
            enrolled={enrolled}
            level={level}
            language={language}
            duration={duration}
            enrollInCourse={enrollInCourse}
            isEnrolled={isEnrolled}
            courseId={Number(courseId)}
            userIdAndToken={idAndToken}
          />
        </div>
      ) : <h2>{errorMessage}</h2>}
      {
        !enrollmentErrorMessage && showEnrollmentMessage
          ? (
            <Modal>
              <ModalMessageComponent
                enrollInCourse={removeModalMessageAfterCourseEnrollment}
              />
            </Modal>
          )
          : <h2>{enrollmentErrorMessage}</h2>
      }
    </>
  );
};
export default CourseDescriptionPage;
