import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import FilterComponent from './Subcomponents/FilterComponent/FilterComponent';
import LearningPathContent from './Subcomponents/LearningPathContent/LearningPathContent';
import {
  ALL_LEARNING_PATHS, FILTER, LAST_INSERTED, RATING, SAVED_LEARNING_PATHS, SORT_BY,
} from '../../constants/Strings';
import styles from './AllLearningPathPage.module.scss';

const AllLearningPathPage = () => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const { token } = userInfo as any;
  const [isVisible, setIsVisible] = useState(true);
  const {
    mainContainer, learningPathsMainContainer,
    learningPathsHeader, sortingContainer,
  } = styles;

  const changeVisibility = () => {
    setIsVisible((prevState) => !prevState);
  };

  return (
    <>
      <NavbarOnSignIn />
      <div className={mainContainer}>
        <FilterComponent changeVisibility={changeVisibility} token={token} />
        <LearningPathContent filterTabIsVisible={isVisible} />
      </div>
    </>
  );
};
export default AllLearningPathPage;
