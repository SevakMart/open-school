import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { Question } from '../../pages/CourseModulePage/DiscussionPage/interfaces/interfaces';
import fetchData from '../../services/fetchData';
import { GetAllQuestionsPayload } from '../interfaces/QuestionActionsSliceInterface';

const initialState = {
  AllquestionsToPeers: [],
  AllquestionsToMentor: [],
  errorMessage: '',
  isLoading: false,
  // section: true,
};

export const AllQuestions = createAsyncThunk(
  'discussionForum/getAllQuestions',
  async (payload: GetAllQuestionsPayload, { rejectWithValue }) => {
    try {
      const {
        enrolledCourseId, token, sectionName, pageNum,
      } = payload;
      const response = await fetchData.get(
        `courses/enrolled/${enrolledCourseId}/${sectionName}-questions`,
        {
          size: pageNum * 15,
          sort: [
            'id,desc',
          ],
        },
        token,
      );
      const responseJSON = await response.json();
      const { content } = responseJSON;
      const filteredContent = content.map((item: Question) => ({
        id: item.id,
        text: item.text,
        createdDate: item.createdDate,
      }));
      return filteredContent;
    } catch (error) {
      return rejectWithValue('Failed to fetch questions');
    }
  },
);

export const GetAllQuestionsSlice = createSlice({
  name: 'AllQuestions',
  initialState,
  reducers: {
  },
  extraReducers: (builder) => {
    builder
      .addCase(AllQuestions.pending, (state) => {
        state.errorMessage = '';
        state.isLoading = true;
      })
      .addCase(AllQuestions.fulfilled, (state, action) => {
        if (action.meta.arg.sectionName === 'peers') {
          state.AllquestionsToPeers = action.payload;
        } else if (action.meta.arg.sectionName === 'mentor') {
          state.AllquestionsToMentor = action.payload;
        }
        state.isLoading = false;
        state.errorMessage = '';
      })
      .addCase(AllQuestions.rejected, (state, action) => {
        state.errorMessage = `${action.payload}`;
        state.isLoading = false;
      });
  },
});

export default GetAllQuestionsSlice.reducer;
