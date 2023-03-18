import { useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import { UserCourseType } from '../../../../types/CourseTypes';
import InProgressCourse from '../InProgressCourse/InProgressCourse';
import CompletedCourse from '../CompletedCourse/CompledtedCourse';
import ContentRenderer from '../../../../component/ContentRenderer/ContentRenderer';
import styles from './MainContent.module.scss';

/* eslint-disable max-len */

const MyLearningPathMainContent = () => {
  const userEnrolledCourseByCourseStatus = useSelector<RootState>((state) => state.userEnrolledCourseByCourseStatus);
  const { entity, isLoading, errorMessage } = userEnrolledCourseByCourseStatus as {entity:any[], isLoading:boolean, errorMessage:string};
  const { courseContainer } = styles;
  return (
    <div className={courseContainer}>
      <ContentRenderer
        isLoading={isLoading}
        errorMessage={errorMessage}
        entity={entity}
        errorFieldClassName="myLearningPathsErrorStyle"
        isMyLearningPathPage
        render={(entity) => (
          entity.map((userCourse:UserCourseType, index:number) => {
            if (userCourse.courseStatus === 'IN_PROGRESS') {
              return (
                <InProgressCourse
                  key={index}
                  title={userCourse.title}
                  courseStatus={userCourse.courseStatus}
                  percentage={userCourse.percentage}
                  remainingTime={userCourse.remainingTime}
                  dueDate={userCourse.dueDate}
                  courseId={userCourse.courseId}
                  id={userCourse.courseId}
                />
              );
            }
            return (
              <CompletedCourse
                key={index}
                title={userCourse.title}
                courseStatus={userCourse.courseStatus}
                grade={userCourse.grade}
              />
            );
          })
        )}
      />
    </div>
  );
};
export default MyLearningPathMainContent;
