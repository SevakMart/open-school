import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import NoCourses from './Subcomponents/NoCourses/NoCourses';
import Header from './Subcomponents/Header/Header';
import InProgressCourse from './Subcomponents/InProgressCourse/InProgressCourse';
import CompletedCourse from './Subcomponents/CompletedCourse/CompledtedCourse';
import SuggestedCourses from './Subcomponents/SuggestedCourses/SuggestedCourses';
import { EXPLORE_COURSES } from '../../constants/Strings';
import { UserCourseType } from '../../types/UserCourseType';
import styles from './MyLearningPathPage.module.scss';

/* eslint-disable max-len */

const MyLearningPathPage = ({ userInfo }:any) => {
  const navigate = useNavigate();
  const { id: userId, token } = userInfo;
  const [userCourses, setUserCourses] = useState<UserCourseType[]>([]);
  const userEnrolledCourseByCourseStatus = useSelector<RootState>((state) => state.userEnrolledCourseByCourseStatus);
  const { entity, isLoading, errorMessage } = userEnrolledCourseByCourseStatus as {entity:any[], isLoading:boolean, errorMessage:string};
  const { courseContainer, exploreCourseButton } = styles;

  const exploreLearningPaths = () => {
    navigate('/exploreLearningPaths');
  };

  return (
    <>
      <Header userId={userId} token={token} />
      <div className={courseContainer}>
        {
          userCourses.length === 0 ? <NoCourses />
            : userCourses.map((userCourse, index) => {
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
            })
        }
        <button type="button" className={exploreCourseButton} onClick={exploreLearningPaths}>{EXPLORE_COURSES}</button>
      </div>
      <SuggestedCourses userId={userId} token={token} />
    </>
  );
};
export default MyLearningPathPage;
