// CodeVerificationPswSlice.ts

import { createSlice } from '@reduxjs/toolkit';
import { VerificationCodePswI } from '../interfaces/VerificationCodePswInterface';

const initialState: VerificationCodePswI = {
  codeDigits: ['', '', '', ''],
};

export const CodeVerificationPswSlice = createSlice({
  name: 'CodeVerificationPsw',
  initialState,
  reducers: {
    setCodeDigits: (state, action) => {
      state.codeDigits = action.payload;
    },
  },
});

export const { setCodeDigits } = CodeVerificationPswSlice.actions;
export default CodeVerificationPswSlice.reducer;
