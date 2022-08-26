import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  isOpen: false,
  buttonType: '',
};

const portalSlice = createSlice({
  name: 'postalStatus',
  initialState,
  reducers: {
    openModal(_, action) {
      return {
        isOpen: true,
        buttonType: action.payload,
      };
    },
    closeModal() {
      return initialState;
    },
  },
});
export const { openModal, closeModal } = portalSlice.actions;
export default portalSlice.reducer;
