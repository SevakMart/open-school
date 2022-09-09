import { combineReducers } from 'redux';
import chooseSubcategoryReducer from './Slices/ChoosSubcategorySlice';
import userInfoReducer from './Slices/loginUserSlice';
import allLearningPathFilterParamsReducer from './Slices/AllLearningPathFilterParamsSlice';
import forgotPasswordEmailReducer from './Slices/ForgotPasswordEmailSlice';
import signedUpUserIdReducer from './Slices/SignedUpUserIdSlice';
import suggestedCourseReducer from './Slices/SuggestedCourseSlice';
import userEnrolledCourseByCourseStatusReducer from './Slices/UserEnrolledCourseByCourseStatus';
import portalStatusReducer from './Slices/PortalOpenStatus';

const rootReducer = combineReducers({
  portalStatus: portalStatusReducer,
  userInfo: userInfoReducer,
  chooseSubcategories: chooseSubcategoryReducer,
  filterParams: allLearningPathFilterParamsReducer,
  forgotPasswordEmail: forgotPasswordEmailReducer,
  signedUpUserId: signedUpUserIdReducer,
  suggestedCourse: suggestedCourseReducer,
  userEnrolledCourseByCourseStatus: userEnrolledCourseByCourseStatusReducer,
});
export default rootReducer;
