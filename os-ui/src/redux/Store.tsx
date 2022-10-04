import { configureStore } from '@reduxjs/toolkit';
import rootReducer from './RootReducer';

export const store = configureStore({
  reducer: rootReducer,
});

export type RootState = ReturnType<typeof store.getState>
export type DispatchType=typeof store.dispatch;
