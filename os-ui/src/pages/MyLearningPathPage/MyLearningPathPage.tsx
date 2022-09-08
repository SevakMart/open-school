import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { RootState } from '../../redux/Store';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import NoCourses from './Subcomponents/NoCourses/NoCourses';
import InProgressCourse from './Subcomponents/InProgressCourse/InProgressCourse';
import CompletedCourse from './Subcomponents/CompletedCourse/CompledtedCourse';
import LearningPath from '../../component/LearningPath/LearningPath';
import {
  MY_LEARNING_PATHS, ALL, IN_PROGRESS, COMPLETED,
  SUGGESTED_LEARNING_PATHS, EXPLORE_COURSES,
} from '../../constants/Strings';
import userService from '../../services/userService';
import { UserCourseType } from '../../types/UserCourseType';
import { SuggestedCourseType } from '../../types/SuggestedCourseType';
import styles from './MyLearningPathPage.module.scss';

enum LearningPathNav {
  All='All',
  InProgress='InProgress',
  Completed='Completed'
}

const MyLearningPathPage = ({ userInfo }:any) => {
  const navigate = useNavigate();
  // const userInfo = useSelector<RootState>((state) => state.userInfo);
  const { t } = useTranslation();
  const [userCourses, setUserCourses] = useState<UserCourseType[]>([]);
  const [suggestedCourses, setSuggestedCourses] = useState<SuggestedCourseType[]>([]);
  const [activeNavType, setActiveNavType] = useState(LearningPathNav.All);
  const {
    mainHeader, courseNavigationBar, activeNav, nonActiveNav, courseContainer,
    suggestedCoursesTitle, suggestedCoursesContainer, exploreCourseButton,
  } = styles;

  const handleNavigation = (e:React.SyntheticEvent) => {
    setActiveNavType((e.target as HTMLParagraphElement).dataset.testid as any);
  };

  const exploreLearningPaths = () => {
    navigate('/exploreLearningPaths');
  };

  useEffect(() => {
    let cancel = false;
    if (!suggestedCourses.length) {
      userService.getSuggestedCourses((userInfo as any).id, (userInfo as any).token)
        .then((data) => {
          if (cancel) return;
          if (!data.errorMessage) {
            setSuggestedCourses(data);
          }
        });
    }
    switch (activeNavType) {
      case LearningPathNav.All:
        userService.getUserCourses((userInfo as any).id, (userInfo as any).token)
          .then((data) => {
            if (cancel) return;
            if (!data.errorMessage) {
              setUserCourses(data);
            }
          });
        break;
      case LearningPathNav.InProgress:
        userService.getUserCourses(
          (userInfo as any).id,
          (userInfo as any).token,
          { courseStatusId: 1 },
        )
          .then((data) => {
            if (cancel) return;
            if (!data.errorMessage) {
              setUserCourses(data);
            }
          });
        break;
      case LearningPathNav.Completed:
        userService.getUserCourses(
          (userInfo as any).id,
          (userInfo as any).token,
          { courseStatusId: 2 },
        )
          .then((data) => {
            if (cancel) return;
            if (!data.errorMessage) {
              setUserCourses(data);
            }
          });
        break;
    }
    return () => { cancel = true; };
  }, [activeNavType]);
  return (
    <>
      <NavbarOnSignIn />
      <h1 className={mainHeader}>{MY_LEARNING_PATHS}</h1>
      <nav className={courseNavigationBar}>
        <p className={activeNavType === 'All' ? activeNav : nonActiveNav} data-testid={LearningPathNav.All} onClick={handleNavigation}>{ALL}</p>
        <p className={activeNavType === 'InProgress' ? activeNav : nonActiveNav} data-testid={LearningPathNav.InProgress} onClick={handleNavigation}>{IN_PROGRESS}</p>
        <p className={activeNavType === 'Completed' ? activeNav : nonActiveNav} data-testid={LearningPathNav.Completed} onClick={handleNavigation}>{COMPLETED}</p>
      </nav>
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
      <p className={suggestedCoursesTitle}>{SUGGESTED_LEARNING_PATHS}</p>
      <div className={suggestedCoursesContainer}>
        {
          suggestedCourses.length ? suggestedCourses.map((suggestedCourse, index) => (
            <LearningPath
              key={index}
              courseInfo={suggestedCourse}
            />
          )) : <h2>{t('No courses.suggested')}</h2>
        }
      </div>
    </>
  );
};
export default MyLearningPathPage;
