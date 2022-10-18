import { createSlice } from '@reduxjs/toolkit';

const initialState = '';

const forgotPasswordEmailSlice = createSlice({
  name: 'forgotPasswordEmail',
  initialState,
  reducers: {
    addEmail(_, action) {
      return action.payload;
    },
    removeEmail() {
      return initialState;
    },
  },
});
export const { addEmail, removeEmail } = forgotPasswordEmailSlice.actions;
export default forgotPasswordEmailSlice.reducer;
