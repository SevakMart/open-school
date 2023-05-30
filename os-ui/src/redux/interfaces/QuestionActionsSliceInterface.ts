import { Question } from '../../pages/CourseModulePage/DiscussionPage/interfaces/interfaces';

export interface PeersState {
  questionsWithId: Question[];
  questionsWithIdToMentor: Question[];
  isLoading: boolean;
  isOpen: boolean;
  section: boolean;
  errorMessage: string;
}

export interface EditQuestionPayload {
  enrolledCourseId: number;
  questionId: string;
  newText: string;
  token: string;
  sectionName: string;
}

export interface RemoveQuestionPayload {
  enrolledCourseId: number;
  questionId: string;
  token: string;
  sectionName: string;
}

export interface PostQuestionsPayload {
  enrolledCourseId: number;
  text: string;
  token: string;
  sectionName: string;
}

// answer
export interface Answer {
  idAnswer: string;
  answerText: string;
  answerCreatedDate: string;
  name: string,
  surname: string,
  questionId: string,
}

export interface AnswersState {
  PeersResponses: {
    questionId: Answer;
  }[];
  MentorResponses: {
    questionId: Answer;
  }[];
  isLoading: boolean;
  isOpenAnswerInput: boolean;
  section: boolean;
  errorMessage: string;
}

export interface PostAnswersPayload {
  enrolledCourseId: number;
  answerText: string;
  token: string;
  sectionName: string;
  questionId: string;
}

// all Questions
export interface GetAllQuestionsPayload {
  enrolledCourseId: number;
  token: string;
  sectionName: string;
}

// all Answers
export interface GetAllAnswersPayload {
  enrolledCourseId: number;
  token: string;
  sectionName: string;
  questionId: string;
}
