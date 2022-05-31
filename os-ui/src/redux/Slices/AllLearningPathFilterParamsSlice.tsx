import { createSlice } from '@reduxjs/toolkit';

interface AllLearningPathFilterParamsType{
    [index:string]:Array<string>|string
}

const initialState:AllLearningPathFilterParamsType = {
  subCategoryIds: [],
  languageIds: [],
  difficultyIds: [],
  courseTitle: '',
};

const allLearningPathFilterParamsSlice = createSlice({
  name: 'paramsSlice',
  initialState,
  reducers: {
    addFilterParams(state, action) {
      if (Array.isArray(state[Object.keys(action.payload)[0]])) {
        (state[Object.keys(action.payload)[0]] as string[])
          .push(Object.values(action.payload)[0] as string);
      } else state[Object.keys(action.payload)[0]] = (Object.values(action.payload)[0] as string);
    },
    removeFilterParams(state, action) {
      if (Array.isArray(state[Object.keys(action.payload)[0]])) {
        const index = (state[Object.keys(action.payload)[0]] as string[]).findIndex(
          (id:string) => id === Object.values(action.payload)[0] as string,
        );
        (state[Object.keys(action.payload)[0]] as string[]).splice(index, 1);
      } else state[Object.keys(action.payload)[0]] = '';
    },
  },
});
export const { addFilterParams, removeFilterParams } = allLearningPathFilterParamsSlice.actions;
export default allLearningPathFilterParamsSlice.reducer;
