/* eslint-disable implicit-arrow-linebreak */
/* eslint-disable array-callback-return */
import { useState } from 'react';
import CourseModuleSidebar from './Subcomponent/CourseModuleSidebar/CourseModuleSidebar';
import styles from './CourseModulePage.module.scss';
import ModuleMainPage from './Subcomponent/ModuleM1Page/ModulMainPage';
import NavbarOnSignIn from '../../component/Navbar-Component/NavbarOnSignIn/NavbarOnSignIn';

const CourseModulePage = ():JSX.Element => {
  const [value, setValue] = useState<string>('1');

  const handleChangeValue = () => {
    setValue(value);
  };

  return (
    <>
      <NavbarOnSignIn />
      <div className={styles.ModuleOverviuw_container}>
        <CourseModuleSidebar value={value} handleChangeValue={handleChangeValue} />
        <ModuleMainPage value={value} handleChangeValue={handleChangeValue} />
      </div>
    </>
  );
};
export default CourseModulePage;
