/* eslint-disable implicit-arrow-linebreak */
/* eslint-disable array-callback-return */
import { useMemo, useState } from 'react';
import { userContext } from '../../contexts/Contexts';
import CourseModuleSidebar from './Subcomponent/CourseModuleSidebar/CourseModuleSidebar';
import styles from './CourseModulePage.module.scss';
import ModuleMainPage from './Subcomponent/ModuleM1Page/ModulMainPage';
import NavbarOnSignIn from '../../component/Navbar-Component/NavbarOnSignIn/NavbarOnSignIn';

const CourseModulePage = ({ userInfo }:{userInfo:any}) => {
  const idAndToken = useMemo(() => ({
	  token: (userInfo as any).token,
	  id: (userInfo as any).id,
  }), []);

  	const [value, setValue] = useState<string>('1');

  const handleChangeValue = () => {
    setValue(value);
  };

  return (
    <>
      <NavbarOnSignIn />
      <userContext.Provider value={idAndToken}>
        <div className={styles.ModuleOverviuw_container}>
          <CourseModuleSidebar value={value} handleChangeValue={handleChangeValue} />
          <ModuleMainPage value={value} handleChangeValue={handleChangeValue} />
        </div>
      </userContext.Provider>
    </>
  );
};
export default CourseModulePage;
