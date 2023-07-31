import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import courseService from '../../services/courseService';
import { clearSearchedQuestions } from './ForumSearchPeersSlice';

interface FindSearchedQuestionMentorPayload {
  enrolledCourseId: number;
  params: {
    page: number;
    size: number;
    q: string;
  };
  token: string;
}

export const findSearchedQuestionMentor = createAsyncThunk('search/findSearchedQuestionMentor', async (payload: FindSearchedQuestionMentorPayload, { rejectWithValue }) => {
  try {
    const { enrolledCourseId, params, token } = payload;

    const response = await courseService.findSearchedQuestionMentor(
      enrolledCourseId,
      params,
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

interface ForumSearchMentorState {
  data: string[];
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
  error: string | null;
}

const initialState: ForumSearchMentorState = {
  data: [],
  status: 'idle',
  error: null,
};

export const forumSearchMentorSlice = createSlice({
  name: 'forumSearchMentor',
  initialState,
  reducers: {
    clearSearchedQuestions: () => initialState,
  },
  extraReducers: (builder) => {
    builder
      .addCase(findSearchedQuestionMentor.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(findSearchedQuestionMentor.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.data = action.payload;
      })
      .addCase(findSearchedQuestionMentor.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string | null;
      })
      .addCase(clearSearchedQuestions.pending, (state) => {
        state.data = [];
        state.status = 'idle';
        state.error = null;
      });
  },
});

export default forumSearchMentorSlice.reducer;
export const clearSearchedQuestionsMentor = forumSearchMentorSlice.actions.clearSearchedQuestions;
