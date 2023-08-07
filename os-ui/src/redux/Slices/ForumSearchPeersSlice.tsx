import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import courseService from '../../services/courseService';

interface FindSearchedQuestionPeersPayload {
  enrolledCourseId: number;
  pageable: {
    page: number;
    size: number;
    q: string;
  };
  token: string;
}

export const findSearchedQuestionPeers = createAsyncThunk('search/findSearchedQuestionPeers', async (params: FindSearchedQuestionPeersPayload, { rejectWithValue }) => {
  try {
    const { enrolledCourseId, pageable, token } = params;

    const response = await courseService.findSearchedQuestionPeers(
      enrolledCourseId,
      pageable,
      token,
    );

    if (response.status === 200) {
      return response.data;
    }
    if (response.status === 404) {
      throw new Error('Code 404: Question not found');
    }
  } catch (error: any) {
    return rejectWithValue(error.message);
  }
});

export const clearSearchedQuestions = createAsyncThunk('search/clearSearchedQuestions', async (_, { rejectWithValue }) => {
  try {
    return null;
  } catch (error: any) {
    return rejectWithValue(error.message);
  }
});

interface SearchState {
	data: string;
	status: 'idle' | 'loading' | 'succeeded' | 'failed';
	error: string | null;
  }

const initialState: SearchState = {
  data: '',
  status: 'idle',
  error: null,
};

export const searchSlice = createSlice({
  name: 'search',
  initialState,
  reducers: {
    clearSearchedQuestions: () => initialState,
  },
  extraReducers: (builder) => {
    builder
      .addCase(findSearchedQuestionPeers.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(findSearchedQuestionPeers.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.data = action.payload;
      })
      .addCase(findSearchedQuestionPeers.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string | null;
      })
      .addCase(clearSearchedQuestions.pending, (state) => {
        state.data = '';
        state.status = 'idle';
        state.error = null;
      });
  },
});

export default searchSlice.reducer;
