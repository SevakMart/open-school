import { combineReducers } from 'redux';
import storage from 'redux-persist/lib/storage';
import storageSession from 'redux-persist/lib/storage/session';
import persistReducer from 'redux-persist/lib/persistReducer';
import portalStatusReducer from './Slices/PortalOpenStatus';
import chooseSubcategoryReducer from './Slices/ChoosSubcategorySlice';
import userInfoReducer from './Slices/loginUserSlice';
import allLearningPathFilterParamsReducer from './Slices/AllLearningPathFilterParamsSlice';

/* const loggedInUserPersistConfig = {
  key: 'loggedInUser',
  storage,
};
const persistConfig = {
  key: 'root',
  storage: storageSession,
  blackList: ['userInfoReducer', 'allLearningPathFilterParamsReducer'],
};

const persistedLoggedInUserReducer = persistReducer(loggedInUserPersistConfig, userInfoReducer);

const rootReducer = combineReducers({
  chooseSubcategories: chooseSubcategoryReducer,
  userInfo: persistedLoggedInUserReducer,
  filterParams: allLearningPathFilterParamsReducer,
});

const persistedRootReducer = persistReducer(persistConfig, rootReducer);
export default persistedRootReducer; */

const rootReducer = combineReducers({
  portalStatus: portalStatusReducer,
  userInfo: userInfoReducer,
  chooseSubcategories: chooseSubcategoryReducer,
  filterParams: allLearningPathFilterParamsReducer,
});
export default rootReducer;
