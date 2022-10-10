import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import userService from '../../services/userService';

export const getSuggestedCourses = createAsyncThunk('suggestedCourse/getSuggestedCourse', async ({ userId, token }:{userId:number, token:string}, { rejectWithValue }) => {
  try {
    const response = await userService.getSuggestedCourses(userId, token);
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
      state.errorMessage = `${action.payload}`;
      state.isLoading = false;
    });
  },
});
export default suggestedCourseSlice.reducer;
