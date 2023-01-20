/* eslint-disable implicit-arrow-linebreak */
/* eslint-disable array-callback-return */
import { useState } from 'react';
import CourseModuleSidebar from './Subcomponent/CourseModuleSidebar/CourseModuleSidebar';
import styles from './CourseModulePage.module.scss';
import ModuleMainPage from './Subcomponent/ModuleM1Page/ModulMainPage';
import { COURSE_CATEGORIES } from '../../constants/CourseModuleCategories';

const CourseModulePage = ():JSX.Element => {
  const [value, setValue] = useState<string>('1');

  const handleChangeValue = () => {
    setValue(value);
  };

  return (
    <div className={styles.ModuleOverviuw_container}>
      <CourseModuleSidebar value={value} handleChangeValue={handleChangeValue} />
      <ModuleMainPage value={value} handleChangeValue={handleChangeValue} />
      {
        COURSE_CATEGORIES.map((module) => {
          const choosenModule = COURSE_CATEGORIES.filter((item) =>
            item.id === module.value);
          console.log(`IN FILTER${choosenModule[0].id}`);
        })
      }
    </div>
  );
};
export default CourseModulePage;
