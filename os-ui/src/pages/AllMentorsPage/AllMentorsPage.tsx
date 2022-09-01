import { useMemo, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import { userContext } from '../../contexts/Contexts';
import { ALL_MENTORS, SAVED_MENTORS } from '../../constants/Strings';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import AllMentorsPageHeader from './Subcomponents/AllMentorsPageHeader/AllMentorsPageHeader';
import Content from './Subcomponents/Content/Content';
import SavedMentors from './Subcomponents/SavedMentors/SavedMentors';
import Search from '../../component/Search/Search';
import styles from './AllMentorPage.module.scss';

const AllMentorsPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const [activeNav, setActiveNav] = useState(ALL_MENTORS);
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const idAndToken = useMemo(() => ({
    token: (userInfo as any).token,
    id: (userInfo as any).id,
  }), []);
  const { mainContainer } = styles;

  const changeHeaderFocus = (headerNav:string) => {
    if (activeNav === ALL_MENTORS || params.get('searchedMentor')) {
      setActiveNav(headerNav);
      navigate('/mentors');
    } else if (activeNav === SAVED_MENTORS) {
      setActiveNav(headerNav);
      navigate(-1);
    }
  };

  const handleSearchQueries = (title:string) => {
    params.set('searchedMentor', title);
    navigate(`/mentors?${params}`);
  };

  return (
    <>
      <NavbarOnSignIn />
      <div className={mainContainer}>
        {/* eslint-disable-next-line max-len */}
        <AllMentorsPageHeader activeNavigator={activeNav} changeHeaderFocus={changeHeaderFocus} />
        {/* <Search changeUrlQueries={handleSearchQueries} /> */}
        <userContext.Provider value={idAndToken}>
          {
            activeNav === ALL_MENTORS ? <Content /> : <SavedMentors />
          }
        </userContext.Provider>

      </div>
    </>
  );
};
export default AllMentorsPage;
