import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import fetchData from '../../services/fetchData';
import { Answer, AnswersState, PostAnswersPayload } from '../interfaces/QuestionActionsSliceInterface';

const initialState: AnswersState = {
  PeersResponses: [],
  MentorResponses: [],
  isLoading: false,
  isOpenAnswerInput: false,
  section: true,
  errorMessage: '',
};

// Define an async thunk for posting questions to the server
export const addAnswer = createAsyncThunk(
  'discussionForum/addAnswer',
  async (payload: PostAnswersPayload, { rejectWithValue }) => {
    try {
      const {
        enrolledCourseId, answerText, token, sectionName, questionId,
      } = payload;
      const body = {
        text: answerText,
        questionId,
      };
      const response = await fetchData.post(
        `courses/enrolled/${enrolledCourseId}/${sectionName}-answers`,
        body,
        {},
        token,
      );
      const responseJSON = await response.json();
      const { id, createdDate, userDto } = responseJSON;
      const newAnswer: Answer = {
        idAnswer: id,
        answerText,
        answerCreatedDate: createdDate,
        name: userDto.name,
        surname: userDto.surname,
        questionId,
      };
      return { questionId: newAnswer };
    } catch (error) {
      return rejectWithValue('Failed to fetch answer');
    }
  },
);

export const AnswerActionsSlice = createSlice({
  name: 'response',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(addAnswer.pending, (state) => {
        state.errorMessage = '';
        state.isLoading = true;
        state.isOpenAnswerInput = false;
      })
      .addCase(addAnswer.fulfilled, (state, action) => {
        if (state.section) state.PeersResponses.push(action.payload);
        else state.MentorResponses.push(action.payload);
        state.isLoading = false;
        state.errorMessage = '';
      })
      .addCase(addAnswer.rejected, (state, action) => {
        state.errorMessage = `${action.payload}`;
        state.isLoading = false;
      });
  },
});

export default AnswerActionsSlice.reducer;
