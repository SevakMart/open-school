import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import userService from '../../services/userService';
import { CourseDescriptionType } from '../../types/CourseTypes';
/* eslint-disable max-len */
export const enrollCourse = createAsyncThunk('enroll/enrollCourse', async ({ userId, courseId, token }:{userId:number, courseId:number, token:string}, { rejectWithValue }) => {
  try {
    const response = await userService.enrollCourse(userId, courseId, token);
    if (response.status === 201) {
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
  entity: {} as CourseDescriptionType,
  isLoading: false,
  errorMessage: '',
};

const enrollCourseSlice = createSlice({
  name: 'enroll',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(enrollCourse.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(enrollCourse.fulfilled, (state, action) => {
      /* if (action.payload.errorMessage) {
        state.errorMessage = action.payload.errorMessage;
      } else {
        state.entity = action.payload;
      } */
      state.entity = action.payload;
      state.isLoading = false;
    });
    builder.addCase(enrollCourse.rejected, (state, action) => {
      state.isLoading = false;
      state.errorMessage = `${action.payload}`;
    });
  },
});
export default enrollCourseSlice.reducer;
