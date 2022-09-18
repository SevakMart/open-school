import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import userService from '../../services/userService';

export const getUserSavedCourse = createAsyncThunk('savedCourse/getUserSavedCourse', async ({ userId, token }:{userId:number, token:string}, { rejectWithValue }) => {
  try {
    const data = await userService.getUserSavedCourses(userId, token, { page: 0, size: 100 });
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
      if (action.payload.errorMessage) {
        state.errorMessage = action.payload.errorMessage;
      } else {
        state.entity = action.payload;
      }
      state.isLoading = false;
    });
  },
});
export default savedCourseSlice.reducer;
