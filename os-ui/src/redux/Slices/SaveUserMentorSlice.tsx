import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import userService from '../../services/userService';

export const saveUserMentor = createAsyncThunk('userMentor/saveUserMentor', async ({ userId, mentorId, token }:{userId:number, mentorId:number, token:string}, { rejectWithValue }) => {
  try {
	  const response = await userService.saveUserMentor(userId, mentorId, token);
	  if (response.status === 200) {
      return { successMessage: 'Success' };
	  }
	  if (response.status === 400) {
      throw new Error(response.data.message);
	  }
  } catch (error:any) {
	  rejectWithValue(error.message);
  }
});

const initialState = {
  isLoading: false,
  errorMessage: '',
};

const userMentorSlice = createSlice({
  name: 'savePreferredCourse',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
	  builder
      .addCase(saveUserMentor.pending, (state) => {
		  state.isLoading = true;
      })
      .addCase(saveUserMentor.fulfilled, (state) => {
		  state.isLoading = false;
      })
      .addCase(saveUserMentor.rejected, (state, action) => {
		  state.errorMessage = `${action.payload}`;
		  state.isLoading = false;
      });
  },
});

export default userMentorSlice.reducer;
