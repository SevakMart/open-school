import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  isOpen: false,
  buttonType: '',
  withSuccessMessage: '',
  isSignUpSuccessfulRegistration: false,
};

const portalSlice = createSlice({
  name: 'postalStatus',
  initialState,
  reducers: {
    openModal(_, action) {
      return {
        isOpen: true,
        buttonType: action.payload,
        withSuccessMessage: '',
        isSignUpSuccessfulRegistration: false,
      };
    },
    openModalWithSuccessMessage(_, action) {
      return {
        isOpen: true,
        buttonType: action.payload.buttonType,
        withSuccessMessage: action.payload.withSuccessMessage,
        isSignUpSuccessfulRegistration: action.payload.isSignUpSuccessfulRegistration,
      };
    },
    closeModal() {
      return initialState;
    },
  },
});
export const { openModal, openModalWithSuccessMessage, closeModal } = portalSlice.actions;
export default portalSlice.reducer;
