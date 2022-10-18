import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import userService from '../../services/userService';
/* eslint-disable max-len */
export const deleteUserSavedCourse = createAsyncThunk('deleteCourse/deleteUserSavedCourse', async ({ userId, courseId, token }:{userId:number, courseId:number, token:string}, { rejectWithValue }) => {
  try {
    const response = await userService.deleteUserSavedCourses(userId, courseId, token);
    if (response.status === 200) {
      return response.data;
    }
    if (response.status === 400) {
      throw new Error(response.data.message);
    }
  } catch (error:any) {
    rejectWithValue(error.message);
  }
});

const initialState = {
  entity: {},
  isLoading: false,
  errorMessage: '',
};
const deletedUserSavedCourseSlice = createSlice({
  name: 'deleteCourse',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(deleteUserSavedCourse.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(deleteUserSavedCourse.fulfilled, (state, action) => {
      state.entity = action.payload;
      state.isLoading = false;
    });
    builder.addCase(deleteUserSavedCourse.rejected, (state, action) => {
      state.isLoading = false;
      state.errorMessage = `${action.payload}`;
    });
  },
});
export default deletedUserSavedCourseSlice.reducer;
