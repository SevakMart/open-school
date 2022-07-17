import { useEffect, useState, useMemo } from 'react';
import { useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { useParams } from 'react-router-dom';
import { RootState } from '../../redux/Store';
import courseService from '../../services/courseService';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import CourseMainContent from './Subcomponents/CourseMainContent/CourseMainContent';
import CourseSummary from './Subcomponents/CourseSummary/CourseSummary';
import { CourseDescriptionType } from '../../types/CourseDescriptionType';
import styles from './CourseDescriptionPage.module.scss';

const CourseDescriptionPage = () => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const [courseInfo, setCourseInfo] = useState({});
  const [errorMessage, setErrorMessage] = useState('');
  const { t } = useTranslation();
  const { courseId } = useParams();
  const idAndToken = useMemo(() => ({
    token: (userInfo as any).token,
    id: (userInfo as any).id,
  }), []);
  const {
    title, description, goal, modules, mentorDto, rating, enrolled, level, language, duration,
  } = courseInfo as CourseDescriptionType;
  const { mainContent } = styles;

  useEffect(() => {
    courseService.requestCourseDescription(Number(courseId), {}, idAndToken.token)
      .then((courseDescriptionData) => setCourseInfo({ ...courseDescriptionData }))
      .catch(() => setErrorMessage(t('Error Message')));
  }, []);

  return (
    <>
      <NavbarOnSignIn />
      {!errorMessage ? (
        <div className={mainContent}>
          <CourseMainContent
            title={title}
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
          />
        </div>
      ) : <h2>{errorMessage}</h2>}
    </>
  );
};
export default CourseDescriptionPage;
