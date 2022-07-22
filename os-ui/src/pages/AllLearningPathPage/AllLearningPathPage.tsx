import { useState, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import { userContext, headerTitleContext } from '../../contexts/Contexts';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import FilterComponent from './Subcomponents/FilterComponent/FilterComponent';
import LearningPathContent from './Subcomponents/LearningPathContent/LearningPathContent';
import LearningPathHeader from './Subcomponents/LearningPathContent/Subcomponents/LearningPathHeader/LearningPathHeader';
import SavedCoursesContent from './Subcomponents/SavedCoursesContent/SavedCoursesContent';
import { ALL_LEARNING_PATHS, SAVED_LEARNING_PATHS } from '../../constants/Strings';

import styles from './AllLearningPathPage.module.scss';

const AllLearningPathPage = () => {
  const navigate = useNavigate();
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const idAndToken = useMemo(() => ({
    token: (userInfo as any).token,
    id: (userInfo as any).id,
  }), []);
  const [changeHeaderFocus, setChangeHeaderFocus] = useState(ALL_LEARNING_PATHS);
  const { mainContainer } = styles;

  const changeNavTitleFocus = (title:string) => {
    if (title === ALL_LEARNING_PATHS) {
      setChangeHeaderFocus(SAVED_LEARNING_PATHS);
      navigate('/exploreLearningPaths');
    } else {
      setChangeHeaderFocus(ALL_LEARNING_PATHS);
      navigate(-1);
    }
  };
  return (
    <>
      <NavbarOnSignIn />
      <userContext.Provider value={idAndToken}>
        <headerTitleContext.Provider value={changeHeaderFocus}>
          <LearningPathHeader handleChangeHeader={changeNavTitleFocus} />
          {changeHeaderFocus === ALL_LEARNING_PATHS
            ? (
              <div className={mainContainer}>
                <FilterComponent />
                <LearningPathContent />
              </div>
            ) : <SavedCoursesContent />}
        </headerTitleContext.Provider>
      </userContext.Provider>
    </>
  );
};
export default AllLearningPathPage;
