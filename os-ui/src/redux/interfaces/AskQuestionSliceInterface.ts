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
