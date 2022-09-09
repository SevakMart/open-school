import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import NavbarOnSignIn from '../../../../component/Navbar-Component/NavbarOnSignIn/NavbarOnSignIn';
import { getUserEnrolledCourseByCourseStatus } from '../../../../redux/Slices/UserEnrolledCourseByCourseStatus';
import styles from './Header.module.scss';

enum LearningPathNav {
    All='All',
    InProgress='InProgress',
    Completed='Completed'
}
/* eslint-disable max-len */
const Header = ({ userId, token }:{userId:number, token:string}) => {
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const [activeNavType, setActiveNavType] = useState(LearningPathNav.All);
  const {
    mainHeader, courseNavigationBar, activeNav, nonActiveNav,
  } = styles;

  const handleNavigation = (e:React.SyntheticEvent) => {
    setActiveNavType((e.target as HTMLParagraphElement).dataset.testid as any);
  };

  useEffect(() => {
    switch (activeNavType) {
      case LearningPathNav.All:
        dispatch(getUserEnrolledCourseByCourseStatus({ userId, token }));
        break;
      case LearningPathNav.InProgress:
        dispatch(getUserEnrolledCourseByCourseStatus({ userId, token, params: { courseStatusId: 1 } }));
        break;
      case LearningPathNav.Completed:
        dispatch(getUserEnrolledCourseByCourseStatus({ userId, token, params: { courseStatusId: 2 } }));
        break;
    }
  }, [activeNavType]);

  return (
    <>
      <NavbarOnSignIn />
      <h1 className={mainHeader}>{t('string.myLearningPaths.mainHeaderTitle')}</h1>
      <nav className={courseNavigationBar}>
        <p className={activeNavType === 'All' ? activeNav : nonActiveNav} data-testid={LearningPathNav.All} onClick={handleNavigation}>{t('string.myLearningPaths.courseStatus.all')}</p>
        <p className={activeNavType === 'InProgress' ? activeNav : nonActiveNav} data-testid={LearningPathNav.InProgress} onClick={handleNavigation}>{t('string.myLearningPaths.courseStatus.inProgress')}</p>
        <p className={activeNavType === 'Completed' ? activeNav : nonActiveNav} data-testid={LearningPathNav.Completed} onClick={handleNavigation}>{t('string.myLearningPaths.courseStatus.completed')}</p>
      </nav>
    </>
  );
};
export default Header;
