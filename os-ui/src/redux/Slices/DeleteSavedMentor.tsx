import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import userService from '../../services/userService';
/* eslint-disable max-len */
export const deleteUserSavedMentor = createAsyncThunk('deleteMentor/deleteUserSavedMentor', async ({ userId, mentorId, token }:{userId:number, mentorId:number, token:string}, { rejectWithValue }) => {
  try {
    const response = await userService.deleteUserSavedMentor(userId, mentorId, token);
    if (response.status === 200) {
      return response.data;
    }
    if (response.status === 400) {
      throw new Error(response.data.message);
    }
  } catch (error:any) {
    rejectWithValue(error.message);
  }
});

const initialState = {
  entity: {},
  isLoading: false,
  errorMessage: '',
};
const deletedUserSavedMentorSlice = createSlice({
  name: 'deleteMentor',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(deleteUserSavedMentor.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(deleteUserSavedMentor.fulfilled, (state, action) => {
      if (action.payload.errorMessage) {
        state.errorMessage = action.payload.errorMessage;
      } else {
        state.entity = action.payload;
      }
      state.isLoading = false;
    });
    builder.addCase(deleteUserSavedMentor.rejected, (state, action) => {
      state.errorMessage = `${action.error.message}`;
    });
  },
});
export default deletedUserSavedMentorSlice.reducer;
