import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import userService from '../../services/userService';
/* eslint-disable max-len */
export const getUserSavedCourse = createAsyncThunk('savedCourse/getUserSavedCourse', async ({ userId, token, params }:{userId:number, token:string, params:object}, { rejectWithValue }) => {
  try {
    const data = await userService.getUserSavedCourses(userId, token, { ...params, page: 0, size: 100 });
    return data.content;
  } catch (error:any) {
    return rejectWithValue(error.message);
  }
});

const initialState = {
  entity: [],
  isLoading: false,
  errorMessage: '',
};

const savedCourseSlice = createSlice({
  name: 'savedCourse',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getUserSavedCourse.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getUserSavedCourse.fulfilled, (state, action) => {
      state.entity = action.payload;
      state.isLoading = false;
    });
    builder.addCase(getUserSavedCourse.rejected, (state, action) => {
      state.errorMessage = `${action.payload}`;
      state.isLoading = false;
    });
  },
});
export default savedCourseSlice.reducer;
