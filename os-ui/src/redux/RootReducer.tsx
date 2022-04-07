import { combineReducers } from 'redux';
import chooseSubcategoryReducer from './Slices/ChoosSubcategorySlice';

const rootReducer = combineReducers({
  chooseSubcategories: chooseSubcategoryReducer,
});

export default rootReducer;
