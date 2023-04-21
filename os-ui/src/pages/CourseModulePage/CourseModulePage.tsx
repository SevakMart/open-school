import { useEffect } from 'react';
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
import { setValue } from '../../redux/Slices/CourseModuleSlice';

const CourseModulePage = ({ userInfo }:{userInfo:any}) => {
  const { entity } = useSelector<RootState>((state) => state.courseDescriptionRequest) as { entity: CourseDescriptionType };
  const { value } = useSelector<RootState>((state) => state.courseModule) as { value: string };

  const { courseId } = useParams();
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(setValue(entity?.modules?.[0]?.title));
  }, [entity]);

  // get CourseDescription from redux
  useEffect(() => {
    dispatch(getCourseDescription({
      courseId: Number(courseId), token: userInfo.token,
    }));
  }, []);

  return (
    value ? (
      <>
        <NavbarOnSignIn />
        <userContext.Provider value={userInfo}>
          <div className={styles.ModuleOverviuw_container}>
            <CourseModuleSidebar />
            <ModuleMainPage />
          </div>
        </userContext.Provider>
      </>
    ) : null
  );
};

export default CourseModulePage;
