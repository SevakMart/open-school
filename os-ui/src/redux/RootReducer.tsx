import { combineReducers } from 'redux';
import chooseSubcategoryReducer from './Slices/ChoosSubcategorySlice';
// import userInfoReducer from './Slices/loginUserSlice';
import allLearningPathFilterParamsReducer from './Slices/AllLearningPathFilterParamsSlice';
import forgotPasswordEmailReducer from './Slices/ForgotPasswordEmailSlice';
import signedUpUserIdReducer from './Slices/SignedUpUserIdSlice';
import suggestedCourseReducer from './Slices/SuggestedCourseSlice';
import userEnrolledCourseByCourseStatusReducer from './Slices/UserEnrolledCourseByCourseStatus';
import allLearningPathCoursesReducer from './Slices/AllLearningPathCourseSlice';
import allMentorsFilterParamsReducer from './Slices/AllMentorsFilterParamsSlice';
import allMentorsSliceReducer from './Slices/AllMentorsSlice';
import savedMentorsReducer from './Slices/SavedMentorsSlice';
import savedCourseReducer from './Slices/SavedLearningPathSlice';
import homepageMentorListReducer from './Slices/HomepageMentorSlice';
import homepageCategoriesListReducer from './Slices/HomepageCategoriesSlice';
import userInfoReducer from './Slices/UserInfoSlice';
import deleteUserSavedCourseReducer from './Slices/DeleteUserSavedCourse';
import deleteUserSavedMentorReducer from './Slices/DeleteSavedMentor';
import courseDescriptionRequestReducer from './Slices/CourseDescriptionRequestSlice';
import enrollCourseReducer from './Slices/EnrollCourseSlice';
import portalStatusReducer from './Slices/PortalOpenStatus';
import AskQuestionReducer from './Slices/AskQuestionSlice';
import CourseModuleReducer from './Slices/CourseModuleSlice';

const rootReducer = combineReducers({
  portalStatus: portalStatusReducer,
  userInfo: userInfoReducer,
  chooseSubcategories: chooseSubcategoryReducer,
  filterParams: allLearningPathFilterParamsReducer,
  forgotPasswordEmail: forgotPasswordEmailReducer,
  signedUpUserId: signedUpUserIdReducer,
  suggestedCourse: suggestedCourseReducer,
  userEnrolledCourseByCourseStatus: userEnrolledCourseByCourseStatusReducer,
  allLearningPathCourses: allLearningPathCoursesReducer,
  allMentorsFilterParams: allMentorsFilterParamsReducer,
  allMentorsList: allMentorsSliceReducer,
  savedMentors: savedMentorsReducer,
  savedCourse: savedCourseReducer,
  homepageMentorList: homepageMentorListReducer,
  homepageCategoriesList: homepageCategoriesListReducer,
  deleteUserSavedCourse: deleteUserSavedCourseReducer,
  deleteUserSavedMentor: deleteUserSavedMentorReducer,
  courseDescriptionRequest: courseDescriptionRequestReducer,
  enrollCourse: enrollCourseReducer,
  AskQuestion: AskQuestionReducer,
  courseModule: CourseModuleReducer,
});
export default rootReducer;
