import { useEffect, useMemo } from 'react';
import { useParams } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../../redux/Store';
import { userContext } from '../../contexts/Contexts';
import NavbarOnSignIn from '../../component/Navbar-Component/NavbarOnSignIn/NavbarOnSignIn';
import CourseMainContent from './Subcomponents/CourseMainContent/CourseMainContent';
import CourseSummary from './Subcomponents/CourseSummary/CourseSummary';
import ContentRenderer from '../../component/ContentRenderer/ContentRenderer';
import { getCourseDescription } from '../../redux/Slices/CourseDescriptionRequestSlice';
import { Portal } from '../../component/Portal/Portal';
import { PortalStatus } from '../../types/PortalStatusType';
import { Types } from '../../types/types';
import EnrolledSuccessMessage from './Subcomponents/CourseSummary/Subcomponent/ModalMessageComponent/ModalMessageComponent';
import { CourseDescriptionType, SuggestedCourseType } from '../../types/CourseTypes';
import styles from './CourseDescriptionPage.module.scss';

const CourseDescriptionPage = ({ userInfo }: { userInfo: any }) => {
  const portalStatus = useSelector<RootState>((state) => state.portalStatus) as PortalStatus;
  const { isOpen, buttonType } = portalStatus;
  const enrollCourseState = useSelector<RootState>((state) => state.enrollCourse) as { entity: SuggestedCourseType, isLoading: boolean, errorMessage: string };
  const { entity: enrolledCourseEntity, isLoading: enrolledCourseLoading, errorMessage: enrolledCourseErrorMessage } = enrollCourseState;
  const { courseId } = useParams();
  const idAndToken = useMemo(() => ({
    token: (userInfo as any).token,
    id: (userInfo as any).id,
  }), []);

  const dispatch = useDispatch();
  const courseDescriptionState = useSelector<RootState>((state) => state.courseDescriptionRequest) as { entity: CourseDescriptionType, isLoading: boolean, errorMessage: string };
  const { entity, isLoading, errorMessage } = courseDescriptionState;
  const {
    mainContent, backgroundFrame, courseSummary, courseMainContent,
  } = styles;
  const currentUserEnrolled = entity && entity.currentUserEnrolled;

  useEffect(() => {
    dispatch(getCourseDescription({ courseId: Number(courseId), token: idAndToken.token }));
  }, []);

  useEffect(() => {
    if (currentUserEnrolled) {
	  document.body.classList.add('course-description-page');
    } else {
	  document.body.classList.remove('course-description-page');
    }
    return () => {
	  document.body.classList.remove('course-description-page');
    };
  }, [currentUserEnrolled]);

  return (
    <>
      <userContext.Provider value={idAndToken}>
        {currentUserEnrolled ? (
          <NavbarOnSignIn currentUserEnrolled={currentUserEnrolled} />
        ) : (
          <div className={backgroundFrame}>
            <NavbarOnSignIn currentUserEnrolled={currentUserEnrolled} />
          </div>
        )}
        <ContentRenderer
          isLoading={isLoading}
          errorMessage={errorMessage}
          entity={entity}
          errorFieldClassName="courseDescriptionError"
          isMyLearningPathPage={false}
          render={(entity) => (
            <div className={mainContent}>
              <div className={courseMainContent}>
                <CourseMainContent
                  description={entity.description}
                  goal={entity.goal}
                  mentorDto={entity.mentorDto}
                  modules={entity.modules}
                  title={entity.title}
                  currentUserEnrolled={currentUserEnrolled}
                  enrolledCourseId={entity.enrolledCourseId}
                  Course_Level={entity.level}
                  Estimated_efforts={entity.duration}
                />
              </div>
              <div className={courseSummary}>
                <CourseSummary
                  Rating={entity.rating}
                  Enrolled={entity.enrolled}
                  Course_Level={entity.level}
                  Language={entity.language}
                  Estimated_efforts={entity.duration}
                  courseId={Number(courseId)}
                  userIdAndToken={idAndToken}
                  title={entity.title}
                  currentUserEnrolled={currentUserEnrolled}
                  enrolledCourseId={entity.enrolledCourseId}
                />
              </div>
            </div>
          )}
        />
      </userContext.Provider>

      <Portal.FormPortal isOpen={isOpen}>
        {isOpen && buttonType === Types.Button.ENROLL_COURSE && (
          <ContentRenderer
            isLoading={enrolledCourseLoading}
            errorMessage={enrolledCourseErrorMessage}
            entity={enrolledCourseEntity}
            errorFieldClassName="enrolmentError"
            isMyLearningPathPage={false}
            render={() => <EnrolledSuccessMessage courseId={Number(courseId)} />}
          />
        )}
      </Portal.FormPortal>
    </>
  );
};

export default CourseDescriptionPage;
