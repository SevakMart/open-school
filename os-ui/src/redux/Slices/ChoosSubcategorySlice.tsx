import { createSlice } from '@reduxjs/toolkit';

const initialState:Array<number> = [];

const chooseSubcategories = createSlice({
  name: 'subcategory',
  initialState,
  reducers: {
    addSubcategory(state, action) {
      state.push(action.payload);
    },
    removeSubcategory(state, action) {
      const index = state.findIndex((id) => id === action.payload);
      state.splice(index, 1);
    },
    removeAllSubcategories() {
      return [];
    },
  },
});
export const {
  addSubcategory,
  removeSubcategory,
  removeAllSubcategories,
} = chooseSubcategories.actions;
export default chooseSubcategories.reducer;
