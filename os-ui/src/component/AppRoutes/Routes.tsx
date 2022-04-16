import { Routes, Route, Navigate } from 'react-router-dom';
import Homepage from '../../pages/Homepage/Homepage';
import ChooseCategoryPage from '../../pages/ChooseCategoryPage/ChooseCategoryPage';

const AppRoutes = () => (
  <Routes>
    <Route path="/homepage" element={<Homepage />} />
    <Route path="/" element={<Navigate replace to="/homepage" />} />
    <Route path="/choose_categories/userId=:id" element={<ChooseCategoryPage />} />
  </Routes>
);
export default AppRoutes;
