import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
import { store } from '../../../../../redux/Store';
import Content from '../Content';

const mockUseNavigate = jest.fn();
jest.mock('react-i18next', () => ({
  ...jest.requireActual('react-i18next'),
  useTranslation: () => ({
    t: (message:string) => {
      if (message === 'messages.noData.default') return 'No data to display';
      return 'Something went wrong please refresh the page';
    },
  }),
}));
jest.mock('redux-state-sync', () => ({
  createStateSyncMiddleware:
    () => () => (next: (action: PayloadAction) => void) => (action: PayloadAction) => next(action),
  initMessageListener: () => jest.fn(),
}));

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useLocation: () => ({
    search: '',
  }),
  useNavigate: () => mockUseNavigate,
}));

describe('Create test cases for Content component', () => {
  test('Create a snapshot test for Content component', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <Content />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
