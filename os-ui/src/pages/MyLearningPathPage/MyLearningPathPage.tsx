import React, { useState } from 'react';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import {
  MY_LEARNING_PATHS, ALL, IN_PROGRESS, COMPLETED,
} from '../../constants/Strings';
import styles from './MyLearningPathPage.module.scss';

enum LearningPathNav {
  All='All',
  InProgress='InProgress',
  Completed='Completed'
}

const MyLearningPathPage = () => {
  const [activeNavType, setActiveNavType] = useState(LearningPathNav.All);
  const {
    mainHeader, courseNavigationBar, activeNav, nonActiveNav,
  } = styles;

  const handleNavigation = (e:React.SyntheticEvent) => {
    setActiveNavType((e.target as HTMLParagraphElement).dataset.testid as any);
  };

  return (
    <>
      <NavbarOnSignIn />
      <h1 className={mainHeader}>{MY_LEARNING_PATHS}</h1>
      <nav className={courseNavigationBar}>
        <p className={activeNavType === 'All' ? activeNav : nonActiveNav} data-testid={LearningPathNav.All} onClick={handleNavigation}>{ALL}</p>
        <p className={activeNavType === 'InProgress' ? activeNav : nonActiveNav} data-testid={LearningPathNav.InProgress} onClick={handleNavigation}>{IN_PROGRESS}</p>
        <p className={activeNavType === 'Completed' ? activeNav : nonActiveNav} data-testid={LearningPathNav.Completed} onClick={handleNavigation}>{COMPLETED}</p>
      </nav>
    </>
  );
};
export default MyLearningPathPage;
