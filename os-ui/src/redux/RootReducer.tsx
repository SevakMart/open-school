import { combineReducers } from 'redux';
import storage from 'redux-persist/lib/storage';
import storageSession from 'redux-persist/lib/storage/session';
import persistReducer from 'redux-persist/lib/persistReducer';
import chooseSubcategoryReducer from './Slices/ChoosSubcategorySlice';
import userInfoReducer from './Slices/loginUserSlice';

const loggedInUserPersistConfig = {
  key: 'loggedInUser',
  storage,
};
const persistConfig = {
  key: 'root',
  storage: storageSession,
  blackList: ['userInfoReducer'],
};

const persistedLoggedInUserReducer = persistReducer(loggedInUserPersistConfig, userInfoReducer);

const rootReducer = combineReducers({
  chooseSubcategories: chooseSubcategoryReducer,
  userInfo: persistedLoggedInUserReducer,
});

const persistedRootReducer = persistReducer(persistConfig, rootReducer);
export default persistedRootReducer;
