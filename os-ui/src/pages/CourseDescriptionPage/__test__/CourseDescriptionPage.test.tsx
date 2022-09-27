import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
import { store } from '../../../redux/Store';
import CourseDescriptionPage from '../CourseDescriptionPage';

/* jest.mock('redux-state-sync', () => ({
  createStateSyncMiddleware:
    () => () => (next: (action: PayloadAction) => void) => (action: PayloadAction) => next(action),
  initMessageListener: () => jest.fn(),
}));
describe('Create test cases for course description page', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(<Provider store={store}>
      <CourseDescriptionPage />
      </Provider>,{ wrapper: BrowserRouter });
    expect(asFragment()).toMatchSnapshot();
  });
});
*/
