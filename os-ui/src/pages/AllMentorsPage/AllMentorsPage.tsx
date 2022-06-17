import { useMemo } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import { userContext } from '../../contexts/Contexts';
import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import AllMentorsPageHeader from './Subcomponents/AllMentorsPageHeader/AllMentorsPageHeader';
import Content from './Subcomponents/Content/Content';

const AllMentorsPage = () => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const idAndToken = useMemo(() => ({
    token: (userInfo as any).token,
    id: (userInfo as any).id,
  }), []);
  return (
    <>
      <NavbarOnSignIn />
      {/* <AllMentorsPageHeader /> */}
      <userContext.Provider value={idAndToken}>
        <Content />
      </userContext.Provider>
    </>
  );
};
export default AllMentorsPage;
