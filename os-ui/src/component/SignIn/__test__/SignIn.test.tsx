import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
import { store } from '../../../redux/Store';
import SignIn from '../SignIn';

const mockNavigate = jest.fn();

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: () => mockNavigate,
}));
jest.mock('redux-state-sync', () => ({
  createStateSyncMiddleware:
    () => () => (next: (action: PayloadAction) => void) => (action: PayloadAction) => next(action),
  initMessageListener: () => jest.fn(),
}));

describe('Create test cases for SignIn component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <SignIn
          handleSignInClicks={() => null}
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
