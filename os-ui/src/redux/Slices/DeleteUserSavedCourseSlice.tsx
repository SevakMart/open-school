import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import userService from '../../services/userService';

export const deleteUserSavedCourse = createAsyncThunk(
  'savedCourse/deleteUserSavedCourse',
  async ({ userId, courseId, token }: { userId: number, courseId: number, token: string }, { rejectWithValue }) => {
    try {
      await userService.deleteUserSavedCourses(userId, courseId, token);
      return courseId;
    } catch (error: any) {
      return rejectWithValue(error.message);
    }
  },
);

const initialState = {
  savedCourses: [] as { id: number }[],
  isLoading: false,
  errorMessage: '',
};

const deleteSavedCourseSlice = createSlice({
  name: 'deleteSavedCourse',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(deleteUserSavedCourse.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(deleteUserSavedCourse.fulfilled, (state, action) => {
        const courseId = action.payload;
        state.isLoading = false;
        state.savedCourses = state.savedCourses.filter((course) => course.id !== courseId);
      })
      .addCase(deleteUserSavedCourse.rejected, (state, action) => {
        state.errorMessage = `${action.payload}`;
        state.isLoading = false;
      });
  },
});

export default deleteSavedCourseSlice.reducer;
