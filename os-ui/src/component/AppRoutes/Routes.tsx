import { Routes, Route, Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import ProtectedRoute from './ProtectedRoute';
import Homepage from '../../pages/Homepage/Homepage';
import ChooseCategoryPage from '../../pages/ChooseCategoryPage/ChooseCategoryPage';
import MyLearningPathPage from '../../pages/MyLearningPathPage/MyLearningPathPage';
import AllLearningPathPage from '../../pages/AllLearningPathPage/AllLearningPathPage';
import SavedCoursesContent from '../../pages/AllLearningPathPage/Subcomponents/SavedCoursesContent/SavedCoursesContent';
import AllMentorsPage from '../../pages/AllMentorsPage/AllMentorsPage';

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
      <Route
        path="/myLearningPath"
        element={(
          <ProtectedRoute token={(userInfo as any).token ? (userInfo as any).token : null}>
            <MyLearningPathPage />
          </ProtectedRoute>
   )}
      />
      <Route
        path="/exploreLearningPaths"
        element={(
          <ProtectedRoute token={(userInfo as any).token ? (userInfo as any).token : null}>
            <AllLearningPathPage />
          </ProtectedRoute>
        )}
      />
      <Route
        path="/exploreLearningPaths/savedCourses"
        element={(
          <ProtectedRoute token={(userInfo as any).token ? (userInfo as any).token : null}>
            <SavedCoursesContent />
          </ProtectedRoute>
        )}
      />
      <Route
        path="/mentors"
        element={(
          <ProtectedRoute token={(userInfo as any).token ? (userInfo as any).token : null}>
            <AllMentorsPage />
          </ProtectedRoute>
        )}
      />
    </Routes>
  );
};
export default AppRoutes;
