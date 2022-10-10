import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import courseService from '../../services/courseService';

/* eslint-disable max-len */

export const getAllLearningPathCourses = createAsyncThunk('get/AllLearningPathCourses', async ({ token, params }:{token:string, params:object}, { rejectWithValue }) => {
  try {
    const data = await courseService.getSearchedCourses({ ...params, page: 0, size: 100 }, token);
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

const allLearningPathCoursesSlice = createSlice({
  name: 'allLearningPathCourses',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getAllLearningPathCourses.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getAllLearningPathCourses.fulfilled, (state, action) => {
      state.entity = action.payload;
      state.isLoading = false;
    });
    builder.addCase(getAllLearningPathCourses.rejected, (state, action) => {
      state.isLoading = false;
      state.errorMessage = `${action.payload}`;
    });
  },
});
export default allLearningPathCoursesSlice.reducer;
