import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
import { store } from '../../../../../redux/Store';
import FilterComponent from '../FilterComponent';

jest.mock('redux-state-sync', () => ({
  createStateSyncMiddleware:
    () => () => (next: (action: PayloadAction) => void) => (action: PayloadAction) => next(action),
  initMessageListener: () => jest.fn(),
}));

describe('Create test cases for FilterComponent component', () => {
  test('Create a snapshot tets', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <FilterComponent />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
