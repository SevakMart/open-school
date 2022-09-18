import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import mentorService from '../../services/mentorService';

export const getSavedMentors = createAsyncThunk('savedMentors/getSavedMentors', async ({ userId, token }:{userId:number, token:string}, { rejectWithValue }) => {
  try {
    const data = await mentorService.requestUserSavedMentors(userId, token, { page: 0, size: 100 });
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

const savedMentorsSlice = createSlice({
  name: 'savedMentors',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getSavedMentors.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getSavedMentors.fulfilled, (state, action) => {
      if (action.payload.errorMessage) {
        state.errorMessage = action.payload.errorMessage;
      } else {
        state.entity = action.payload;
      }
      state.isLoading = false;
    });
  },
});
export default savedMentorsSlice.reducer;
