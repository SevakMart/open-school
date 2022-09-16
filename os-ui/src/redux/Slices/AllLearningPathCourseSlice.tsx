import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import courseService from '../../services/courseService';

/* eslint-disable max-len */

export const getAllLearningPathCourses = createAsyncThunk('get/AllLearningPathCourses', async ({ token, params }:{token:string, params:object}) => {
  try {
    const data = await courseService.getSearchedCourses({ ...params, page: 0, size: 100 }, token);
    return data.content;
  } catch (error:any) {
    throw new Error(error.message);
  }
});

const initialState = {
  entity: [],
  isLoading: false,
  errorMessage: '',
};

const allLearningPathCoursesSlice = createSlice({
  name: 'allLearningPathCourses',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getAllLearningPathCourses.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getAllLearningPathCourses.fulfilled, (state, action) => {
      state.isLoading = false;
      state.entity = action.payload;
    });
    builder.addCase(getAllLearningPathCourses.rejected, (state, action) => {
      if (action.payload instanceof Error) {
        state.errorMessage = action.payload.message;
      }
    });
  },
});
export default allLearningPathCoursesSlice.reducer;
