import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import userService from '../../services/userService';

/* eslint-disable max-len */

export const getUserEnrolledCourseByCourseStatus = createAsyncThunk('userEnrolledCourseByCourseStatus/getUserEnrolledCourseByCourseStatus', async ({ userId, token, params = {} }:{userId:number, token:string, params?:object}, { rejectWithValue }) => {
  try {
    const response = await userService.getUserCourses(userId, token, params);
    if (response.status === 200) {
      return response.data;
    }
    if (response.status === 400) {
      throw new Error(`Code 400: ${response.data.message}`);
    }
  } catch (error:any) {
    return rejectWithValue(error.message);
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
      if (action.payload.errorMessage) {
        state.errorMessage = action.payload.errorMessage;
      } else {
        state.entity = action.payload;
      }
      state.isLoading = false;
    });
    builder.addCase(getUserEnrolledCourseByCourseStatus.rejected, (state, action) => ({ ...initialState, errorMessage: `${action.payload}` }));
  },
});
export default courseByCourseStatusSlice.reducer;
