import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import NoCourses from '../../component/NoCourses/NoCourses';
import LearningPath from '../../component/LearningPath/LearningPath';
import {
  MY_LEARNING_PATHS, ALL, IN_PROGRESS, COMPLETED, USER_URL, SUGGESTED_LEARNING_PATHS,
} from '../../constants/Strings';
import { getUserCourses } from '../../services/getUserCourses';
import { getSuggestedCourses } from '../../services/getSuggestedCourses';
import { UserCourseType } from '../../types/UserCourseType';
import { SuggestedCourseType } from '../../types/SuggestedCourseType';
import styles from './MyLearningPathPage.module.scss';

enum LearningPathNav {
  All='All',
  InProgress='InProgress',
  Completed='Completed'
}

const MyLearningPathPage = () => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const [userCourses, setUserCourses] = useState<UserCourseType[]>([]);
  const [suggestedCourses, setSuggestedCourses] = useState<SuggestedCourseType[]>([]);
  const [activeNavType, setActiveNavType] = useState(LearningPathNav.All);
  const {
    mainHeader, courseNavigationBar, activeNav, nonActiveNav, courseContainer,
    suggestedCoursesTitle, suggestedCoursesContainer,
  } = styles;

  const handleNavigation = (e:React.SyntheticEvent) => {
    setActiveNavType((e.target as HTMLParagraphElement).dataset.testid as any);
  };

  useEffect(() => {
    getUserCourses(`${USER_URL}/${(userInfo as any).id}/courses`)
      .then((data) => {
        if (!data.errorMessage) {
          setUserCourses(data);
        }
      });
  }, [userCourses]);
  useEffect(() => {
    getSuggestedCourses(`${USER_URL}/${(userInfo as any).id}/courses/suggested`)
      .then((data) => {
        if (!data.errorMessage) {
          setSuggestedCourses(data);
        }
      });
  }, []);
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
          userCourses.length === 0 ? <NoCourses /> : null
        }
      </div>
      <p className={suggestedCoursesTitle}>{SUGGESTED_LEARNING_PATHS}</p>
      <div className={suggestedCoursesContainer}>
        {
          suggestedCourses.length ? suggestedCourses.map((suggestedCourse, index) => (
            <LearningPath
              key={index}
              title={suggestedCourse.title}
              rating={suggestedCourse.rating}
              difficulty={suggestedCourse.difficulty}
              keywords={suggestedCourse.keywords}
            />
          )) : null
        }
      </div>
    </>
  );
};
export default MyLearningPathPage;
