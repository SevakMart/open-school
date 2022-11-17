import { Suspense, useMemo, useState } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/Store';
import Loader from '../Loader/Loader';
import ProtectedRoute from './ProtectedRoute';
import Homepage from '../../pages/Homepage/Homepage';
import ChooseCategoryPage from '../../pages/ChooseCategoryPage/ChooseCategoryPage';
import MyLearningPathPage from '../../pages/MyLearningPathPage/MyLearningPathPage';
import AllLearningPathPage from '../../pages/AllLearningPathPage/AllLearningPathPage';
import AllMentorsPage from '../../pages/AllMentorsPage/AllMentorsPage';
import NotFoundPage from '../../pages/NotFoundPage/NotFoundPage';
import AfterVerificationPage from '../../pages/AfterVerificationPage/AfterVerificationPage';
import CourseDescriptionPage from '../../pages/CourseDescriptionPage/CourseDescriptionPage';
import { signInContext } from '../../contexts/Contexts';

/* eslint-disable max-len */

const AppRoutes = () => {
  const userInfoState = useSelector<RootState>((state) => state.userInfo);
  const { userInfo } = userInfoState as any;
  const [signIn, setSignIn] = useState(false);

  const signInInfo: any = useMemo(
    () => ({ signIn, setSignIn }),
    [signIn],
  );

  return (
    <signInContext.Provider value={signInInfo}>
      <Suspense fallback={<Loader />}>
        <Routes>
          <Route path="/homepage" element={<Homepage userInfo={userInfo} />} />
          <Route path="/homepage/account" element={<AfterVerificationPage />} />
          <Route path="/" element={<Navigate replace to="/homepage" />} />
          <Route
            path="/categories/subcategories"
            element={(
              <ProtectedRoute token={(userInfo && (userInfo as any).token) ? (userInfo as any).token : null}>
                <ChooseCategoryPage userInfo={userInfo} />
              </ProtectedRoute>
     )}
          />
          <Route
            path="/myLearningPath"
            element={(
              <ProtectedRoute token={(userInfo && (userInfo as any).token) ? (userInfo as any).token : null}>
                <MyLearningPathPage userInfo={userInfo} />
              </ProtectedRoute>
   )}
          />
          <Route
            path="/exploreLearningPaths"
            element={(
              <ProtectedRoute token={(userInfo && (userInfo as any).token) ? (userInfo as any).token : null}>
                <AllLearningPathPage userInfo={userInfo} />
              </ProtectedRoute>
        )}
          />
          <Route
            path="/mentors"
            element={(
              <ProtectedRoute token={(userInfo && (userInfo as any).token) ? (userInfo as any).token : null}>
                <AllMentorsPage userInfo={userInfo} />
              </ProtectedRoute>
        )}
          />
          <Route
            path="/userCourse/:courseId"
            element={(
              <ProtectedRoute token={(userInfo && (userInfo as any).token) ? (userInfo as any).token : null}>
                <CourseDescriptionPage userInfo={userInfo} />
              </ProtectedRoute>
        )}
          />
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </Suspense>
    </signInContext.Provider>
  );
};
export default AppRoutes;
