import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import NoCourses from '../../component/NoCourses/NoCourses';
import {
  MY_LEARNING_PATHS, ALL, IN_PROGRESS, COMPLETED, USER_URL,
} from '../../constants/Strings';
import { getUserCourses } from '../../services/getUserCourses';
import { UserCourseType } from '../../types/UserCourseType';
import styles from './MyLearningPathPage.module.scss';

enum LearningPathNav {
  All='All',
  InProgress='InProgress',
  Completed='Completed'
}

const MyLearningPathPage = () => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const [userCourses, setUserCourses] = useState<UserCourseType[]>([]);
  const [activeNavType, setActiveNavType] = useState(LearningPathNav.All);
  const {
    mainHeader, courseNavigationBar, activeNav, nonActiveNav, courseContainer,
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
    </>
  );
};
export default MyLearningPathPage;
