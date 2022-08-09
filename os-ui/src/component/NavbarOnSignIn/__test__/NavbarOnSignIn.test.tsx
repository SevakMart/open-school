import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
import NavbarOnSignIn from '../NavbarOnSignIn';
import { store } from '../../../redux/Store';

const mockedUseNavigate = jest.fn();

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: () => mockedUseNavigate,
}));
jest.mock('redux-state-sync', () => ({
  createStateSyncMiddleware:
    () => () => (next: (action: PayloadAction) => void) => (action: PayloadAction) => next(action),
  initMessageListener: () => jest.fn(),
}));

test('Create a Snapshot test', () => {
  const { asFragment } = render(<Provider store={store}><NavbarOnSignIn /></Provider>);
  expect(asFragment()).toMatchSnapshot();
});
