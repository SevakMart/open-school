import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import { Question } from '../../pages/CourseModulePage/DiscussionPage/interfaces/interfaces';
import fetchData from '../../services/fetchData';
import {
  EditQuestionPayload, PeersState, PostQuestionsPayload, RemoveQuestionPayload,
} from '../interfaces/QuestionActionsSliceInterface';

// Define the initial state for the "peers" slice
const initialState: PeersState = {
  questionsWithId: [],
  questionsWithIdToMentor: [],
  isLoading: false,
  isOpen: false,
  section: true,
  errorMessage: '',
};

// Define an async thunk for posting questions to the server
export const addQuestion = createAsyncThunk(
  'discussionForum/addQuestion',
  async (payload: PostQuestionsPayload, { rejectWithValue }) => {
    try {
      const {
        enrolledCourseId, text, token, sectionName,
      } = payload;
      const body = {
        text,
      };
      const response = await fetchData.post(
        `courses/enrolled/${enrolledCourseId}/${sectionName}-questions`,
        body,
        {},
        token,
      );
      const responseJSON = await response.json();
      const { id, createdDate, userDto } = responseJSON;
      const newQuestion: Question = {
        id,
        text,
        createdDate,
        answers: [],
        name: userDto.name,
        surname: userDto.surname,
      };
      return newQuestion;
    } catch (error) {
      return rejectWithValue('Failed to fetch questions');
    }
  },
);

// Define an async thunk for editing a question
export const updateQuestion = createAsyncThunk(
  'discussionForum/updateQuestion',
  async (payload: EditQuestionPayload, { rejectWithValue }) => {
    try {
      const {
        enrolledCourseId, questionId, newText, token, sectionName,
      } = payload;

      const body = {
        text: newText,
      };
      const response = await fetchData.put(
        `courses/enrolled/${enrolledCourseId}/${sectionName}-questions/${questionId}`,
        body,
        {},
        token,
      );
      const responseJSON = await response.json();
      const updatedQuestion: Question = {
        id: questionId,
        text: newText,
        createdDate: responseJSON.createdDate,
        answers: [],
      };
      return updatedQuestion;
    } catch (error) {
      return rejectWithValue('Failed to edit Question');
    }
  },
);

// Define an async thunk for removing a question
export const removeQuestion = createAsyncThunk(
  'discussionForum/removeQuestion',
  async (payload: RemoveQuestionPayload, { rejectWithValue }) => {
    try {
      const {
        enrolledCourseId, questionId, token, sectionName,
      } = payload;
      await fetchData.delete(
        `courses/enrolled/${enrolledCourseId}/${sectionName}-questions/${questionId}`,
        {},
        token,
      );
      return payload.questionId;
    } catch (error) {
      return rejectWithValue('Failed to remove question');
    }
  },
);

export const QuestionActionsSlice = createSlice({
  name: 'peers',
  initialState,
  reducers: {
    onClose: (state) => {
      state.isOpen = false;
    },
    onOpen: (state) => {
      state.isOpen = true;
    },
    changeSection: (state, action) => {
      state.section = action.payload;
    },
    AllQuestionsPeersFromServer: (state, action) => {
      state.questionsWithId = action.payload;
    },
    AllQuestionsmentorFromServer: (state, action) => {
      state.questionsWithIdToMentor = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(addQuestion.pending, (state) => {
        state.errorMessage = '';
        state.isLoading = true;
        state.isOpen = false;
      })
      .addCase(addQuestion.fulfilled, (state, action) => {
        // Update state with the newly posted question
        if (state.section) state.questionsWithId.unshift(action.payload);
        else state.questionsWithIdToMentor.unshift(action.payload);
        state.isLoading = false;
        state.errorMessage = '';
      })
      .addCase(addQuestion.rejected, (state, action) => {
        // Update state with error message when addQuestion request is rejected
        state.errorMessage = `${action.payload}`;
        state.isLoading = false;
      })
      .addCase(updateQuestion.pending, (state) => {
        state.errorMessage = '';
        state.isLoading = true;
      })
      .addCase(updateQuestion.fulfilled, (state, action) => {
        const { id } = action.payload;
        const currentQuestionPeers = state.questionsWithId.find((question) => question.id === id);
        if (currentQuestionPeers && state.section) currentQuestionPeers.text = action.payload.text;
        const currentQuestionMentor = state.questionsWithIdToMentor.find((question) => question.id === id);
        if (currentQuestionMentor && !state.section) currentQuestionMentor.text = action.payload.text;
        state.isLoading = false;
        state.errorMessage = '';
      })
      .addCase(updateQuestion.rejected, (state, action) => {
        state.isLoading = false;
        state.errorMessage = `${action.payload}`;
      })
      .addCase(removeQuestion.pending, (state) => {
        state.isLoading = true;
        state.errorMessage = '';
      })
      .addCase(removeQuestion.fulfilled, (state, action: PayloadAction<string>) => {
        const questionIdToRemove = action.payload;
        if (state.section) state.questionsWithId = state.questionsWithId.filter((question) => question.id !== questionIdToRemove);
        else state.questionsWithId = state.questionsWithIdToMentor.filter((question) => question.id !== questionIdToRemove);
        state.isLoading = false;
        state.errorMessage = '';
      })
      .addCase(removeQuestion.rejected, (state, action) => {
        state.isLoading = false;
        state.errorMessage = `${action.payload}`;
      });
  },
});

export const {
  onOpen, onClose, changeSection, AllQuestionsPeersFromServer, AllQuestionsmentorFromServer,
} = QuestionActionsSlice.actions;
export default QuestionActionsSlice.reducer;
