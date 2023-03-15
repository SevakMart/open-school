import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import { userContext } from '../../contexts/Contexts';
import CourseModuleSidebar from './Subcomponent/CourseModuleSidebar/CourseModuleSidebar';
import styles from './CourseModulePage.module.scss';
import ModuleMainPage from './Subcomponent/ModuleM1Page/ModulMainPage';
import NavbarOnSignIn from '../../component/Navbar-Component/NavbarOnSignIn/NavbarOnSignIn';
import { getCourseDescription } from '../../redux/Slices/CourseDescriptionRequestSlice';
import { RootState } from '../../redux/Store';
import { CourseDescriptionType } from '../../types/CourseTypes';

const CourseModulePage = ({ userInfo }: { userInfo: any }) => {
  const { entity } = useSelector<RootState>((state) => state.courseDescriptionRequest) as { entity: CourseDescriptionType };
  const [value, setValue] = useState<string>(entity?.modules?.[0]?.title || '');
  const { courseId } = useParams();
  const dispatch = useDispatch();

  useEffect(() => {
    setValue(entity?.modules?.[0]?.title);
  }, [entity]);

  useEffect(() => {
    dispatch(getCourseDescription({
      courseId: Number(courseId), token: userInfo.token,
    }));
  }, []);

  const handleChangeValue = (newValue: string) => {
    setValue(newValue);
  };

  return (
    value ? (
      <>
        <NavbarOnSignIn />
        <userContext.Provider value={userInfo}>
          <div className={styles.ModuleOverviuw_container}>
            <CourseModuleSidebar value={value} handleChangeValue={handleChangeValue} title={entity.title} modules={entity.modules} />
            <ModuleMainPage value={value} handleChangeValue={handleChangeValue} modules={entity.modules} duration={entity.duration} />
          </div>
        </userContext.Provider>
      </>
	  ) : null
  );
};

export default CourseModulePage;
