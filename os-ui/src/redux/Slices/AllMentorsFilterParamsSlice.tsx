import { createSlice } from '@reduxjs/toolkit';

export interface MentorStateType {
  name:string;
  page:number;
  size:number;
}

const initialState = {
  name: '',
  page: 0,
  size: 100,
};

const mentorsParamsSlice = createSlice({
  name: 'mentorsParams',
  initialState,
  reducers: {
    addMentorNameToParams(state, action) {
      state.name = action.payload;
    },
    removeMentorNameFromParams() {
      return initialState;
    },
  },
});
export const { addMentorNameToParams, removeMentorNameFromParams } = mentorsParamsSlice.actions;
export default mentorsParamsSlice.reducer;
