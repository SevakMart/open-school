import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import mentorService from '../../services/mentorService';
import { MentorStateType } from './AllMentorsFilterParamsSlice';
/* eslint-disable max-len */
export const getMentorsList = createAsyncThunk('mentorsList/getMentorsList', async ({ params, token }:{params:MentorStateType, token:string}, { rejectWithValue }) => {
  try {
    if (params.name) {
      const data = await mentorService.searchMentorsByName(token, params);
      return data.content;
    }
    const response = await mentorService.requestAllMentors(token, params);
    return response.data.content;
  } catch (error:any) {
    return rejectWithValue(error.message);
  }
});

const initialState = {
  entity: [],
  isLoading: false,
  errorMessage: '',
};

const allMentorsListSlice = createSlice({
  name: 'mentorsList',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getMentorsList.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getMentorsList.fulfilled, (state, action) => {
      state.entity = action.payload;
      state.isLoading = false;
    });
    builder.addCase(getMentorsList.rejected, (state, action) => {
      state.isLoading = false;
      state.errorMessage = `${action.payload}`;
    });
  },
});
export default allMentorsListSlice.reducer;
