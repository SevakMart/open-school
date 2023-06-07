import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import userService from '../../services/userService';

export const saveUserPreferredCourse = createAsyncThunk(
  'savedCourse/saveUserPreferredCourse',
  async ({ userId, courseId, token }:{userId:number, courseId:number, token:string}, { rejectWithValue }) => {
    try {
      const data = await userService.saveUserPreferredCourses(userId, courseId, token);
      return data;
    } catch (error:any) {
      return rejectWithValue(error.message);
    }
  },
);

const initialState = {
  isLoading: false,
  errorMessage: '',
};

const savePreferredCourseSlice = createSlice({
  name: 'savePreferredCourse',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(saveUserPreferredCourse.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(saveUserPreferredCourse.fulfilled, (state) => {
        state.isLoading = false;
      })
      .addCase(saveUserPreferredCourse.rejected, (state, action) => {
        state.errorMessage = `${action.payload}`;
        state.isLoading = false;
      });
  },
});

export default savePreferredCourseSlice.reducer;
