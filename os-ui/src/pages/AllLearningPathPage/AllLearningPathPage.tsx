import { useMemo } from 'react';
import { userContext } from '../../contexts/Contexts';
import NavbarOnSignIn from '../../component/Navbar-Component/NavbarOnSignIn/NavbarOnSignIn';
import MainContent from './Subcomponents/MainContent/MainContent';

const AllLearningPathPage = ({ userInfo }:{userInfo:object}) => {
  const idAndToken = useMemo(() => ({
    token: (userInfo as any).token,
    id: (userInfo as any).id,
  }), []);

  return (
    <>
      <NavbarOnSignIn currentUserEnrolled />
      <userContext.Provider value={idAndToken}>
        <MainContent />
      </userContext.Provider>
    </>
  );
};
export default AllLearningPathPage;
