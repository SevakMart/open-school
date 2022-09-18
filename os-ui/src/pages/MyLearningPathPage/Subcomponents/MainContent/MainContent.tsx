import { useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import Loader from '../../../../component/Loader/Loader';
import NoCourses from '../NoCourses/NoCourses';
import InProgressCourse from '../InProgressCourse/InProgressCourse';
import CompletedCourse from '../CompletedCourse/CompledtedCourse';
import { ErrorField } from '../../../../component/ErrorField/ErrorField';
import styles from './MainContent.module.scss';

/* eslint-disable max-len */

const MyLearningPathMainContent = () => {
  const userEnrolledCourseByCourseStatus = useSelector<RootState>((state) => state.userEnrolledCourseByCourseStatus);
  const { entity, isLoading, errorMessage } = userEnrolledCourseByCourseStatus as {entity:any[], isLoading:boolean, errorMessage:string};
  const { courseContainer } = styles;
  return (
    <div className={courseContainer}>
      {isLoading && <Loader />}
      {errorMessage !== '' && (
      <ErrorField.MainErrorField className={['myLearningPathsErrorStyle']}>
        {errorMessage}
      </ErrorField.MainErrorField>
      )}
      {entity.length === 0 && !isLoading && errorMessage.length === 0 && <NoCourses /> }
      {entity.length > 0 && entity.map((userCourse, index) => {
        if (userCourse.courseStatus === 'IN_PROGRESS') {
          return (
            <InProgressCourse
              key={index}
              title={userCourse.title}
              courseStatus={userCourse.courseStatus}
              percentage={userCourse.percentage}
              remainingTime={userCourse.remainingTime}
              dueDate={userCourse.dueDate}
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
      }) }
    </div>
  );
};
export default MyLearningPathMainContent;
