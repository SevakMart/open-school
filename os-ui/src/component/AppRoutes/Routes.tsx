import { Routes, Route, Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { useState } from 'react';
import { RootState } from '../../redux/Store';
import ProtectedRoute from './ProtectedRoute';
import Homepage from '../../pages/Homepage/Homepage';
import ChooseCategoryPage from '../../pages/ChooseCategoryPage/ChooseCategoryPage';
import MyLearningPathPage from '../../pages/MyLearningPathPage/MyLearningPathPage';
import NotFound from '../../pages/NotFound/NotFound';

const AppRoutes = () => {
  const [isVerify, setIsVerify] = useState(true);
  const verifyInfo = {
    status: true,
    error: false,
    data: {
      id: 'jgr874gefrt8',
    },
  };

  const userInfo = useSelector<RootState>((state) => state.userInfo);
  return (
    <Routes>
      <Route path="/homepage" element={<Homepage />} />
      <Route path="/" element={<Navigate replace to="/homepage" />} />
      {isVerify && <Route path="/verify/1" element={isVerify ? <ChooseCategoryPage /> : <NotFound />} />}
      <Route
        path="/categories/subcategories/userId=:id"
        element={(
          <ProtectedRoute token={(userInfo as any).token ? (userInfo as any).token : null}>
            <ChooseCategoryPage />
          </ProtectedRoute>
     )}
      />
      <Route
        path="/myLearningPath"
        element={(
          <ProtectedRoute token={(userInfo as any).token ? (userInfo as any).token : null}>
            <MyLearningPathPage />
          </ProtectedRoute>
   )}
      />
    </Routes>
  );
};
export default AppRoutes;
