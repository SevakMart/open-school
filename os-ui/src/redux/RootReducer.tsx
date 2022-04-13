import { combineReducers } from 'redux';
// import chooseSubcategoryReducer from './Slices/ChoosSubcategorySlice';
import storage from 'redux-persist/lib/storage';
import persistReducer from 'redux-persist/lib/persistReducer';
import userInfoReducer from './Slices/loginUserSlice';

const loggedInUserPersistConfig = {
  key: 'loggedInUser',
  storage,
};

const persistedLoggedInUserReducer = persistReducer(loggedInUserPersistConfig, userInfoReducer);

const rootReducer = combineReducers({
  // chooseSubcategories: chooseSubcategoryReducer,
  userInfo: persistedLoggedInUserReducer,
});

export default rootReducer;
