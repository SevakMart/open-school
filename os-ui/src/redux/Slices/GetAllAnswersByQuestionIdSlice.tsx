import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { Question } from '../../pages/CourseModulePage/DiscussionPage/interfaces/interfaces';
import fetchData from '../../services/fetchData';
import { GetAllAnswersPayload } from '../interfaces/QuestionActionsSliceInterface';

const initialState = {
  AllAnswersByQuIdToPeers: [],
  AllAnswersByQuIdToMentor: [],
  errorMessage: '',
  isLoading: false,
  section: true,
};

export const AllAnswersByQuId = createAsyncThunk(
  'discussionForum/getAllAnswers',
  async (payload: GetAllAnswersPayload, { rejectWithValue }) => {
    try {
      const {
        enrolledCourseId, token, sectionName, questionId,
      } = payload;
      const response = await fetchData.get(
        `courses/enrolled/${enrolledCourseId}/${sectionName}-questions/answers${questionId}`,
        {},
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

export const GetAllAnswersByQuestionIdSlice = createSlice({
  name: 'AllAnswersByQuId',
  initialState,
  reducers: {
    // changeSectionValue: (state, action) => {
    //   state.section = action.payload;
    // },
  },
  extraReducers: (builder) => {
    builder
      .addCase(AllAnswersByQuId.pending, (state) => {
        state.errorMessage = '';
        state.isLoading = true;
      })
      .addCase(AllAnswersByQuId.fulfilled, (state, action) => {
        if (action.meta.arg.sectionName === 'peers') {
          state.AllAnswersByQuIdToPeers = action.payload;
        } else if (action.meta.arg.sectionName === 'mentor') {
          state.AllAnswersByQuIdToMentor = action.payload;
        }
        state.isLoading = false;
        state.errorMessage = '';
      })
      .addCase(AllAnswersByQuId.rejected, (state, action) => {
        state.errorMessage = `${action.payload}`;
        state.isLoading = false;
      });
  },
});

// export const { changeSectionValue } = GetAllQuestionsSlice.actions;
export default GetAllAnswersByQuestionIdSlice.reducer;
