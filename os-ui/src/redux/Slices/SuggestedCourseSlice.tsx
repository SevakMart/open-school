import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import userService from '../../services/userService';

export const getSuggestedCourses = createAsyncThunk('get/getSuggestedCourse', async ({ userId, token }:{userId:number, token:string}) => {
  const response = await userService.getSuggestedCourses(userId, token);
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
const suggestedCourseSlice = createSlice({
  name: 'suggestedCourse',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getSuggestedCourses.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getSuggestedCourses.fulfilled, (state, action) => {
      state.entity = action.payload;
      state.isLoading = false;
    });
    builder.addCase(getSuggestedCourses.rejected, (state, action) => {
      if (action.payload instanceof Error) {
        state.errorMessage = action.payload.message;
      } else {
        state.errorMessage = action.payload as string;
      }
      state.isLoading = false;
    });
  },
});
export default suggestedCourseSlice.reducer;
