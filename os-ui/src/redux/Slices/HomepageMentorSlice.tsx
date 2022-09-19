import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import publicService from '../../services/publicService';
import mentorService from '../../services/mentorService';

export const getHomepageMentorsList = createAsyncThunk('homepageMentorsList/getHomepageMentorsList', async ({ page, token }:{page:number, token?:string}, { rejectWithValue }) => {
  try {
    if (token) {
      const data = await mentorService.requestAllMentors(token, { page, size: 4 });
      return data;
    }
    const response = await publicService.getPublicMentors({ page, size: 4 });
    return response.data;
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
      if (action.payload.errorMessage) {
        state.errorMessage = action.payload.errorMessage;
      } else {
        state.entity = action.payload.content;
        state.totalPages = action.payload.totalPages - 1;
      }
      state.isLoading = false;
    });
  },
});
export default homepageMentorsSlice.reducer;
