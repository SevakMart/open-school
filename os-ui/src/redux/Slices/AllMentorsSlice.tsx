import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import mentorService from '../../services/mentorService';
import { MentorStateType } from './AllMentorsFilterParamsSlice';
/* eslint-disable max-len */
export const getMentorsList = createAsyncThunk('get/MentorList', async ({ params, token }:{params:MentorStateType, token:string}) => {
  if (params.name !== '') {
    try {
      const data = await mentorService.searchMentorsByName(token, params);
      return data.content;
    } catch (error:any) {
      throw new Error(error.message);
    }
  } else {
    try {
      const data = await mentorService.requestAllMentors(token, params);
      return data.content;
    } catch (error:any) {
      throw new Error(error.message);
    }
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
      state.isLoading = false;
      state.entity = action.payload;
    });
    builder.addCase(getMentorsList.rejected, (state, action) => {
      if (action.payload instanceof Error) {
        state.errorMessage = action.payload.message;
      }
    });
  },
});
export default allMentorsListSlice.reducer;
