import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import courseService from '../../services/courseService';
import { SuggestedCourseType } from '../../types/SuggestedCourseType';
import userService from '../../services/userService';

type CourseListType=SuggestedCourseType & {id:number, isBookMarked?:boolean}
/* eslint-disable max-len */

export const getAllLearningPathCourses = createAsyncThunk('get/AllLearningPathCourses', async ({ userId, token, params }:{userId:number, token:string, params:object}) => {
  try {
    const combinedData = await Promise.all([
      userService.getUserSavedCourses(userId, token, { page: 0, size: 100 }),
      courseService.getSearchedCourses({ ...params, page: 0, size: 100 }, token),
    ]);
    const userSavedCourseContent = combinedData[0].content;
    const searchedCourseContent = combinedData[1].content;
    const list = [];
    if (userSavedCourseContent?.length) {
      for (const searchedCourse of searchedCourseContent) {
        const index = userSavedCourseContent
          .findIndex((savedCourse:CourseListType) => savedCourse.id === searchedCourse.id);
        if (index !== -1) {
          userSavedCourseContent[index].isBookMarked = true;
          list.push(userSavedCourseContent[index]);
        } else {
          searchedCourse.isBookMarked = false;
          list.push(searchedCourse);
        }
      } return list;
    } return searchedCourseContent;
  } catch (error:any) {
    throw new Error(error.message);
  }
});
const initialState = {
  entity: [],
  isLoading: false,
  errorMessage: '',
};

const allLearningPathCoursesSlice = createSlice({
  name: 'allLearningPathCourses',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getAllLearningPathCourses.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getAllLearningPathCourses.fulfilled, (state, action) => {
      state.isLoading = false;
      state.entity = action.payload;
    });
    builder.addCase(getAllLearningPathCourses.rejected, (state, action) => {
      if (action.payload instanceof Error) {
        state.errorMessage = action.payload.message;
      }
    });
  },
});
export default allLearningPathCoursesSlice.reducer;
