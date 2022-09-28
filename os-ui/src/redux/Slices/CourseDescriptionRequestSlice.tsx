import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import courseService from '../../services/courseService';
import { CourseDescriptionType } from '../../types/CourseTypes';
/* eslint-disable max-len */
export const getCourseDescription = createAsyncThunk('getCourse/getCourseDescription', async ({ courseId, token }:{courseId:number, token:string}, { rejectWithValue }) => {
  try {
    const response = await courseService.requestCourseDescription(courseId, {}, token);
    if (response.status === 200) {
      return response.data;
    }
    if (response.status === 404) {
      throw new Error('Code 404: Course not found');
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

const courseDescriptionSlice = createSlice({
  name: 'getCourse',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getCourseDescription.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getCourseDescription.fulfilled, (state, action) => {
      if (action.payload.errorMessage) {
        state.errorMessage = action.payload.errorMessage;
      } else {
        state.entity = action.payload;
      }
      state.isLoading = false;
    });
    builder.addCase(getCourseDescription.rejected, (state, action) => {
      state.errorMessage = `${action.error.message}`;
      state.isLoading = false;
    });
  },
});
export default courseDescriptionSlice.reducer;
