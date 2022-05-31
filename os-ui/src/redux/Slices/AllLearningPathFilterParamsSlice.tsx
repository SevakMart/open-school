import { createSlice } from '@reduxjs/toolkit';

interface AllLearningPathFilterParamsType{
    [index:string]:Array<string>
}

const initialState:AllLearningPathFilterParamsType = {
  subCategoryIds: [],
  languageIds: [],
  difficultyIds: [],
};

const allLearningPathFilterParamsSlice = createSlice({
  name: 'paramsSlice',
  initialState,
  reducers: {
    addFilterParams(state, action) {
      console.log(action.payload);
      state[Object.keys(action.payload)[0]].push(Object.values(action.payload)[0] as string);
    },
    removeFilterParams(state, action) {
      const index = state[Object.keys(action.payload)[0]].findIndex(
        (id) => id === Object.values(action.payload)[0] as string,
      );
      state[Object.keys(action.payload)[0]].splice(index, 1);
    },
  },
});
export const { addFilterParams, removeFilterParams } = allLearningPathFilterParamsSlice.actions;
export default allLearningPathFilterParamsSlice.reducer;
