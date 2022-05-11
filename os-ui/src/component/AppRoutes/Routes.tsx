import { Routes, Route, Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import ProtectedRoute from './ProtectedRoute';
import Homepage from '../../pages/Homepage/Homepage';
import ChooseCategoryPage from '../../pages/ChooseCategoryPage/ChooseCategoryPage';

const AppRoutes = () => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  return (
    <Routes>
      <Route path="/homepage" element={<Homepage />} />
      <Route path="/" element={<Navigate replace to="/homepage" />} />
      <Route
        path="/categories/subcategories"
        element={(
          <ProtectedRoute token={(userInfo as any).token ? (userInfo as any).token : null}>
            <ChooseCategoryPage />
          </ProtectedRoute>
     )}
      />
    </Routes>
  );
};
export default AppRoutes;
