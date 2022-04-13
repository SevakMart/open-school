import { createSlice } from '@reduxjs/toolkit';

const initialState = {};

const userInfoSlice = createSlice({
  name: 'userInfo',
  initialState,
  reducers: {
    addLoggedInUser(state, action) {
      return { ...action.payload };
    },
    removeLoggedInUser() {
      return {};
    },
  },
});
export const { addLoggedInUser, removeLoggedInUser } = userInfoSlice.actions;
export default userInfoSlice.reducer;
