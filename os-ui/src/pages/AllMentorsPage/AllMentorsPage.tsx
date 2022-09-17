import { useMemo } from 'react';
import { userContext } from '../../contexts/Contexts';
import MainContent from './Subcomponents/MainContent/MainContent';
import NavbarOnSignIn from '../../component/Navbar-Component/NavbarOnSignIn/NavbarOnSignIn';

const AllMentorsPage = ({ userInfo }:{userInfo:any}) => {
  const idAndToken = useMemo(() => ({
    token: (userInfo as any).token,
    id: (userInfo as any).id,
  }), []);

  return (
    <>
      <NavbarOnSignIn />
      <userContext.Provider value={idAndToken}>
        <MainContent />
      </userContext.Provider>
    </>
  );
};
export default AllMentorsPage;
