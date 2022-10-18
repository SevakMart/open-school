import { createSlice } from '@reduxjs/toolkit';

const initialState = -1;

const signedUpUserId = createSlice({
  name: 'SignedUpUserId',
  initialState,
  reducers: {
    addSignedUpUserId(_, action) {
      return action.payload;
    },
    removeSignedUpUserId() {
      return initialState;
    },
  },
});
export const { addSignedUpUserId, removeSignedUpUserId } = signedUpUserId.actions;
export default signedUpUserId.reducer;
