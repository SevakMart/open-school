import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import publicService from '../../services/publicService';
import mentorService from '../../services/mentorService';

export const getHomepageMentorsList = createAsyncThunk('homepageMentorsList/getHomepageMentorsList', async ({ page, token }:{page:number, token?:string}, { rejectWithValue }) => {
  try {
    if (token) {
      const response = await mentorService.requestAllMentors(token, { page, size: 4 });
      if (response.status === 200) {
        return response.data;
      }

      throw new Error('An error occurred while fetching mentors please refresh the page');
    }
    const response = await publicService.getPublicMentors({ page, size: 4 });
    if (response.status === 200) {
      return response.data;
    }

    throw new Error('An error occurred while fetching mentors please refresh the page');
  } catch (error:any) {
    return rejectWithValue(error.message);
  }
});

const initialState = {
  entity: [],
  isLoading: false,
  errorMessage: '',
  totalPages: 0,
};

const homepageMentorsSlice = createSlice({
  name: 'homepageMentorsList',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getHomepageMentorsList.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getHomepageMentorsList.fulfilled, (state, action) => {
      /* if (action.payload.errorMessage) {
        state.errorMessage = action.payload.errorMessage;
      } else {
        state.entity = action.payload.content;
        state.totalPages = action.payload.totalPages - 1;
      } */
      state.entity = action.payload.content;
      state.totalPages = action.payload.totalPages - 1;
      state.isLoading = false;
    });
    builder.addCase(getHomepageMentorsList.rejected, (state, action) => {
      state.isLoading = false;
      state.errorMessage = `${action.payload}`;
    });
  },
});
export default homepageMentorsSlice.reducer;
