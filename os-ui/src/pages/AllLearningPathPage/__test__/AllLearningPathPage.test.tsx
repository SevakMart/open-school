import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
import { store } from '../../../redux/Store';
import AllLearningPathPage from '../AllLearningPathPage';

const mockUseNavigate = jest.fn();
const mockUseLocation = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
  useLocation: () => mockUseLocation,
}));
jest.mock('redux-state-sync', () => ({
  createStateSyncMiddleware:
    () => () => (next: (action: PayloadAction) => void) => (action: PayloadAction) => next(action),
  initMessageListener: () => jest.fn(),
}));
jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));
const userInfo = {
  token: '123',
  id: 1,
};
describe('Create test cases for AllLearningPathPage component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <AllLearningPathPage userInfo={userInfo} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
