import { createSlice } from '@reduxjs/toolkit';
import { storage } from '../../services/storage/storage';

const initialState = {
  userInfo: storage.getItemFromLocalStorage('userInfo'),
};

const userInfoSlice = createSlice({
  name: 'userinfo',
  initialState,
  reducers: {
    addUserInfoToLocalStorage(state, action) {
      storage.addItemToLocalStorage('userInfo', action.payload);
      state.userInfo = action.payload;
    },
    removeUserInfoFromLocalStorage(state) {
      storage.removeItemFromLocalStorage('userInfo');
      state.userInfo = undefined;
    },
  },
});
export const { addUserInfoToLocalStorage, removeUserInfoFromLocalStorage } = userInfoSlice.actions;
export default userInfoSlice.reducer;
