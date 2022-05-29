import { useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import { tokenContext } from '../../contexts/Contexts';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import FilterComponent from './Subcomponents/FilterComponent/FilterComponent';
import LearningPathContent from './Subcomponents/LearningPathContent/LearningPathContent';
import styles from './AllLearningPathPage.module.scss';

const AllLearningPathPage = () => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const { token } = userInfo as any;
  const [isVisible, setIsVisible] = useState(true);
  const { mainContainer } = styles;

  const changeVisibility = () => {
    setIsVisible((prevState) => !prevState);
  };

  return (
    <>
      <NavbarOnSignIn />
      <tokenContext.Provider value={token}>
        <div className={mainContainer}>
          <FilterComponent changeVisibility={changeVisibility} />
          <LearningPathContent filterTabIsVisible={isVisible} />
        </div>
      </tokenContext.Provider>
    </>
  );
};
export default AllLearningPathPage;
