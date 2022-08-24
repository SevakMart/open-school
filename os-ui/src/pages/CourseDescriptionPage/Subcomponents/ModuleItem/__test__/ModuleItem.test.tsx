import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
import { store } from '../../../../../redux/Store';
import ModuleItem from '../ModuleItem';

const moduleInfo = {
  title: 'React js',
  description: 'It talks about React',
  moduleItemSet: [{ title: 'Redux' }],
};
jest.mock('redux-state-sync', () => ({
  createStateSyncMiddleware:
    () => () => (next: (action: PayloadAction) => void) => (action: PayloadAction) => next(action),
  initMessageListener: () => jest.fn(),
}));

describe('Create test cases for ModuleItem component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <ModuleItem moduleInfo={moduleInfo} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
