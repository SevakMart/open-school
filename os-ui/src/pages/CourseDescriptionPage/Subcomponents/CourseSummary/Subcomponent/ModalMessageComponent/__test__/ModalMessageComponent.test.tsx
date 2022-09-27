import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
import { store } from '../../../../../../../redux/Store';
import ModalMessageComponent from '../ModalMessageComponent';

jest.mock('redux-state-sync', () => ({
  createStateSyncMiddleware:
    () => () => (next: (action: PayloadAction) => void) => (action: PayloadAction) => next(action),
  initMessageListener: () => jest.fn(),
}));
describe('Create test cases for ModalMessageComponent', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <ModalMessageComponent />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
