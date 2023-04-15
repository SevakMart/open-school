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
import DiscussionForum from './DiscussionPage/DiscussionForum';

const CourseModulePage = ({ userInfo }:{userInfo:any}) => {
  const [isDiscBtnpressed, setDiscBtnpressed] = useState<boolean>(
    sessionStorage.getItem('isDiscBtnpressed') === 'true' || false, // Initializing to false if value not found in sessionStorage
  );
  const { entity } = useSelector<RootState>((state) => state.courseDescriptionRequest) as { entity: CourseDescriptionType };
  const [value, setValue] = useState<string>(entity?.modules?.[0]?.title || '');
  const { courseId } = useParams();
  const dispatch = useDispatch();
  const setDisBtnPosition = (value: string): void => {
    if (value === 'Discussion Forum') {
      setDiscBtnpressed(() => !isDiscBtnpressed);
    }
  };
  useEffect(() => {
    setValue(entity?.modules?.[0]?.title);
  }, [entity]);

  // Save the value in sessionStorage only if the page is being unloaded (e.g. only after refresh)
  useEffect(() => {
    const handleUnload = () => sessionStorage.setItem('isDiscBtnpressed', isDiscBtnpressed.toString());
    window.addEventListener('beforeunload', handleUnload);
    return () => {
      window.removeEventListener('beforeunload', handleUnload);
    };
  }, [isDiscBtnpressed]);

  // Remove the value from sessionStorage when the page is loaded (e.g. after the user navigates back to the page)
  useEffect(() => {
    const handleLoad = () => sessionStorage.removeItem('isDiscBtnpressed');
    window.addEventListener('load', handleLoad);
    return () => {
      window.removeEventListener('load', handleLoad);
    };
  }, []);

  // get CourseDescription from redux
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
            <CourseModuleSidebar value={value} handleChangeValue={handleChangeValue} title={entity.title} modules={entity.modules} setDisBtnPosition={setDisBtnPosition} />
            { isDiscBtnpressed === false
              ? (
                <ModuleMainPage value={value} handleChangeValue={handleChangeValue} modules={entity.modules} duration={entity.duration} />
              )
              : (<DiscussionForum userInfo={userInfo} />
              )}
          </div>
        </userContext.Provider>
      </>
	  ) : null
  );
};

export default CourseModulePage;
