import { Routes, Route, Navigate } from 'react-router-dom';
import Homepage from '../../pages/Homepage/Homepage';

const AppRoutes = () => (
  <Routes>
    <Route path="/homepage" element={<Homepage />} />
    <Route path="/" element={<Navigate replace to="/homepage" />} />
  </Routes>
);
export default AppRoutes;
