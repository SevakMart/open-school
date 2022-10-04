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
/* eslint-disable max-len */
const CourseDescriptionPage = ({ userInfo }:{userInfo:any}) => {
  const portalStatus = useSelector<RootState>((state) => state.portalStatus) as PortalStatus;
  const { isOpen, buttonType } = portalStatus;
  const enrollCourseState = useSelector<RootState>((state) => state.enrollCourse) as {entity:SuggestedCourseType, isLoading:boolean, errorMessage:string};
  const { entity: enrolledCourseEntity, isLoading: enrolledCourseLoading, errorMessage: enrolledCourseErrorMessage } = enrollCourseState;
  const { courseId } = useParams();
  const idAndToken = useMemo(() => ({
    token: (userInfo as any).token,
    id: (userInfo as any).id,
  }), []);
  const dispatch = useDispatch();
  const courseDescriptionState = useSelector<RootState>((state) => state.courseDescriptionRequest) as {entity:CourseDescriptionType, isLoading:boolean, errorMessage:string};
  const { entity, isLoading, errorMessage } = courseDescriptionState;
  const { mainContent } = styles;

  useEffect(() => {
    dispatch(getCourseDescription({ courseId: Number(courseId), token: idAndToken.token }));
  }, []);

  return (
    <>
      <NavbarOnSignIn />
      <userContext.Provider value={idAndToken}>
        <ContentRenderer
          isLoading={isLoading}
          errorMessage={errorMessage}
          entity={entity}
          errorFieldClassName="courseDescriptionError"
          isMyLearningPathPage={false}
          render={(entity) => (
            <div className={mainContent}>
              <CourseMainContent
                description={entity.description}
                goal={entity.goal}
                mentorDto={entity.mentorDto}
                modules={entity.modules}
                title={entity.title}
              />
              <CourseSummary
                rating={entity.rating}
                enrolled={entity.enrolled}
                level={entity.level}
                language={entity.language}
                duration={entity.duration}
                courseId={Number(courseId)}
                userIdAndToken={idAndToken}
                title={entity.title}
              />
            </div>
          )}
        />
      </userContext.Provider>
      <Portal.FormPortal isOpen={isOpen}>
        {isOpen && buttonType === Types.Button.ENROLL_COURSE
        && (
        <ContentRenderer
          isLoading={enrolledCourseLoading}
          errorMessage={enrolledCourseErrorMessage}
          entity={enrolledCourseEntity}
          errorFieldClassName="enrolmentError"
          isMyLearningPathPage={false}
          render={() => <EnrolledSuccessMessage />}
        />
        )}
      </Portal.FormPortal>
    </>
  );
};
export default CourseDescriptionPage;
