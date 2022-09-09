import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import userService from '../../services/userService';

/* eslint-disable max-len */

export const getUserEnrolledCourseByCourseStatus = createAsyncThunk('get/courseByCourseStatus', async ({ userId, token, params = {} }:{userId:number, token:string, params?:object}) => {
  const response = await userService.getUserCourses(userId, token, params);
  if (response.status === 200) {
    return response.data;
  }
  if (response.status === 400) {
    throw response.data.message;
  } else {
    throw new Error('An error occurred please reload the page');
  }
});

const initialState = {
  entity: [],
  isLoading: false,
  errorMessage: '',
};

const courseByCourseStatusSlice = createSlice({
  name: 'userEnrolledCourseByCourseStatus',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getUserEnrolledCourseByCourseStatus.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getUserEnrolledCourseByCourseStatus.fulfilled, (state, action) => {
      state.entity = action.payload;
      state.isLoading = false;
    });
    builder.addCase(getUserEnrolledCourseByCourseStatus.rejected, (state, action) => {
      if (action.payload instanceof Error) {
        state.errorMessage = action.payload.message;
      } else {
        state.errorMessage = action.payload as string;
      }
      state.isLoading = false;
    });
  },
});
export default courseByCourseStatusSlice.reducer;
