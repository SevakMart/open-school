import { useMemo, useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import { userContext } from '../../contexts/Contexts';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import FilterComponent from './Subcomponents/FilterComponent/FilterComponent';
import LearningPathContent from './Subcomponents/LearningPathContent/LearningPathContent';
import styles from './AllLearningPathPage.module.scss';

const AllLearningPathPage = () => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const tokenAndId = useMemo(
    () => ({ token: (userInfo as any).token, id: (userInfo as any).id }),
    [userInfo],
  );
  const [isVisible, setIsVisible] = useState(true);
  const { mainContainer } = styles;

  const changeVisibility = () => {
    setIsVisible((prevState) => !prevState);
  };

  return (
    <>
      <NavbarOnSignIn />
      <userContext.Provider value={tokenAndId}>
        <div className={mainContainer}>
          <FilterComponent changeVisibility={changeVisibility} />
          <LearningPathContent
            filterTabIsVisible={isVisible}
          />
        </div>
      </userContext.Provider>
    </>
  );
};
export default AllLearningPathPage;
