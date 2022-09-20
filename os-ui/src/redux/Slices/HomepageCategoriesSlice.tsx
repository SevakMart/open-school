import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import categoriesService from '../../services/categoriesService';
import publicService from '../../services/publicService';

export const getHomepageCategoriesList = createAsyncThunk('homepageMentorsList/getHomepageCategoriesList', async ({ page, token }:{page:number, token?:string}, { rejectWithValue }) => {
  try {
    if (token) {
      const response = await categoriesService.getCategories({ page, size: 6 }, token);
      return response.data;
    }
    const response = await publicService.getPublicCategories({ page, size: 6 });
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

const homepageCategoriesSlice = createSlice({
  name: 'homepageCategoriesList',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getHomepageCategoriesList.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getHomepageCategoriesList.fulfilled, (state, action) => {
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
export default homepageCategoriesSlice.reducer;
