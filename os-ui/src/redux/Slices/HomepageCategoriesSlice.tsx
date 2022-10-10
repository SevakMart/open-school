import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import categoriesService from '../../services/categoriesService';
import publicService from '../../services/publicService';

export const getHomepageCategoriesList = createAsyncThunk('homepageMentorsList/getHomepageCategoriesList', async ({ page, token }:{page:number, token?:string}, { rejectWithValue }) => {
  try {
    if (token) {
      const response = await categoriesService.getCategories({ page, size: 6 }, token);
      if (response.status === 200) {
        return response.data;
      }

      throw new Error('An error occurred while fetching categories please refresh the page');
    }
    const response = await publicService.getPublicCategories({ page, size: 6 });
    if (response.status === 200) {
      return response.data;
    }

    throw new Error('An error occurred while fetching categories please refresh the page');
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
      state.entity = action.payload.content;
      state.totalPages = action.payload.totalPages - 1;
      state.isLoading = false;
    });
    builder.addCase(getHomepageCategoriesList.rejected, (state, action) => {
      state.isLoading = false;
      state.errorMessage = `${action.payload}`;
    });
  },
});
export default homepageCategoriesSlice.reducer;
