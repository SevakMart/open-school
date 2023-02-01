/* eslint-disable implicit-arrow-linebreak */
/* eslint-disable array-callback-return */
import { useState } from 'react';
import CourseModuleSidebar from './Subcomponent/CourseModuleSidebar/CourseModuleSidebar';
import styles from './CourseModulePage.module.scss';
import ModuleMainPage from './Subcomponent/ModuleM1Page/ModulMainPage';
import { COURSE_CATEGORIES } from '../../constants/CourseModuleCategories';
import NavbarOnSignIn from '../../component/Navbar-Component/NavbarOnSignIn/NavbarOnSignIn';
import DiscussionForm from './DiscussionPage/DiscussionForm';

const CourseModulePage = ():JSX.Element => {
  const [isDiscBtnpressed, setDiscBtnpressed] = useState<boolean>(false);
  const setDisBtnPosition = (value:string):void => {
    if (value === 'Discussion Form') {
      setDiscBtnpressed(() => !isDiscBtnpressed);
    }
  };
  const [value, setValue] = useState<string>('1');

  const handleChangeValue = () => {
    setValue(value);
  };

  return (
    <>
      <NavbarOnSignIn />
      <div className={styles.ModuleOverviuw_container}>
        <CourseModuleSidebar value={value} handleChangeValue={handleChangeValue} setDisBtnPosition={setDisBtnPosition} />
        { isDiscBtnpressed === false
          ? (
            <ModuleMainPage value={value} handleChangeValue={handleChangeValue} />
          )
          : (<DiscussionForm />
          )}
        {COURSE_CATEGORIES.map((module) => {
          const choosenModule = COURSE_CATEGORIES.filter((item) => item.id === module.value);
        })}
      </div>
    </>
  );
};
export default CourseModulePage;
