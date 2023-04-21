import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  value: '',
};

export const CourseModuleSlice = createSlice({
  name: 'courceModule',
  initialState,
  reducers: {
    setValue: (state, action) => {
      state.value = action.payload;
    },
  },
});

export const { setValue } = CourseModuleSlice.actions;
export default CourseModuleSlice.reducer;
