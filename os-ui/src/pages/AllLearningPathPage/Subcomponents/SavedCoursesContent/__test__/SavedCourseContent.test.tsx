import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
import { store } from '../../../../../redux/Store';
import SavedCoursesContent from '../SavedCoursesContent';
/* eslint-disable max-len */

jest.mock('react-i18next', () => ({
  useTranslation: () => ({
    t: () => 'No data to display',
  }),
}));

jest.mock('redux-state-sync', () => ({
  createStateSyncMiddleware:
    () => () => (next: (action: PayloadAction) => void) => (action: PayloadAction) => next(action),
  initMessageListener: () => jest.fn(),
}));

const mockUseNavigate = jest.fn();
const mockUseLocation = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
  useLocation: () => mockUseLocation,
}));

describe('Create test cases for SavedCourseContent component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(<Provider store={store}><SavedCoursesContent /></Provider>, { wrapper: BrowserRouter });
    expect(asFragment()).toMatchSnapshot();
  });
});
