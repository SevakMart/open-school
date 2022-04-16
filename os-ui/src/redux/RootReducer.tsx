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
};
const persistedChosenSubcategories = persistReducer(persistConfig, chooseSubcategoryReducer);

const persistedLoggedInUserReducer = persistReducer(loggedInUserPersistConfig, userInfoReducer);

const rootReducer = combineReducers({
  chooseSubcategories: persistedChosenSubcategories,
  userInfo: persistedLoggedInUserReducer,
});

export default rootReducer;
