import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  isOpen: false,
  buttonType: '',
  withSuccessMessage: '',
  isSignUpSuccessfulRegistration: false,
  isResetPasswordSuccessfulMessage: false,
  isResendVerificationEmailMessage: false,
  isRequestForMentorsPage: false,
};

const portalSlice = createSlice({
  name: 'postalStatus',
  initialState,
  reducers: {
    openModal(_, action) {
      return { ...initialState, isOpen: true, ...action.payload };
    },
    openModalWithSuccessMessage(_, action) {
      return { ...initialState, isOpen: true, ...action.payload };
    },
    closeModal() {
      return initialState;
    },
  },
});
export const { openModal, openModalWithSuccessMessage, closeModal } = portalSlice.actions;
export default portalSlice.reducer;
