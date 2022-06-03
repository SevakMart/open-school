import { useMemo, useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import { userContext, courseContentContext } from '../../contexts/Contexts';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import FilterComponent from './Subcomponents/FilterComponent/FilterComponent';
import LearningPathContent from './Subcomponents/LearningPathContent/LearningPathContent';
import { CourseContent } from '../../types/CourseContent';
import styles from './AllLearningPathPage.module.scss';

const AllLearningPathPage = () => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const userTokenAndId = useMemo(
    () => ({ token: (userInfo as any).token, userId: (userInfo as any).id }),
    [userInfo],
  );
  const [isVisible, setIsVisible] = useState(true);
  const [contentType, setContentType] = useState(CourseContent.ALLCOURSES);
  const { mainContainer } = styles;

  const changeVisibility = () => {
    setIsVisible((prevState) => !prevState);
  };
  const changeContentType = (contentType:CourseContent) => {
    setContentType(contentType);
  };
  return (
    <>
      <NavbarOnSignIn />
      <userContext.Provider value={userTokenAndId}>
        <courseContentContext.Provider value={contentType}>
          <div className={mainContainer}>
            <FilterComponent changeVisibility={changeVisibility} />
            <LearningPathContent
              filterTabIsVisible={isVisible}
              changeContentType={changeContentType}
            />
          </div>
        </courseContentContext.Provider>
      </userContext.Provider>
    </>
  );
};
export default AllLearningPathPage;
