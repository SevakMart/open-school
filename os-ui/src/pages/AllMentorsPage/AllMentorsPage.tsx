import { useMemo, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import { userContext } from '../../contexts/Contexts';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import AllMentorsPageHeader from './Subcomponents/AllMentorsPageHeader/AllMentorsPageHeader';
import Content from './Subcomponents/Content/Content';
import Search from '../../component/Search/Search';
import styles from './AllMentorPage.module.scss';

const AllMentorsPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const idAndToken = useMemo(() => ({
    token: (userInfo as any).token,
    id: (userInfo as any).id,
  }), []);
  const { mainContainer } = styles;

  const handleSearchQueries = (title:string) => {
    params.set('searchedMentor', title);
    navigate(`/mentors?${params}`);
  };

  return (
    <>
      <NavbarOnSignIn />
      <div className={mainContainer}>
        <AllMentorsPageHeader activeNavigator="All Mentors Nav" />
        <Search changeUrlQueries={handleSearchQueries} />
        <userContext.Provider value={idAndToken}>
          <Content />
        </userContext.Provider>
      </div>
    </>
  );
};
export default AllMentorsPage;
