import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
import { store } from '../../../redux/Store';
import AllMentorsPage from '../AllMentorsPage';

/* const mockedUseNavigation = jest.fn();
const mockedUseLocation = jest.fn();

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: () => mockedUseNavigation,
  useLocation: () => mockedUseLocation,
}));
jest.mock('redux-state-sync', () => ({
  createStateSyncMiddleware:
    () => () => (next: (action: PayloadAction) => void) => (action: PayloadAction) => next(action),
  initMessageListener: () => jest.fn(),
}));

describe('Create test case for AllMentorsPage', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <AllMentorsPage />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
*/
