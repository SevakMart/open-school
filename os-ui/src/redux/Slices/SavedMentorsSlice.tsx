import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { MentorStateType } from './AllMentorsFilterParamsSlice';
import mentorService from '../../services/mentorService';

export const getSavedMentors = createAsyncThunk('savedMentors/getSavedMentors', async ({ userId, token, params }:{userId:number, token:string, params:MentorStateType}, { rejectWithValue }) => {
  try {
    if (params.name) {
      const data = await mentorService.searchSavedMentorsByName(userId, token, params);
      return data.content;
    }
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
