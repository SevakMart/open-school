import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import MyLearningPathPage from '../MyLearningPathPage';

const userInfo = {
  token: '123',
  id: 1,
};
const mockUseNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
  useLocation: () => ({
    search: '',
  }),
}));
jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));

describe('Create several unit tests for MyLearningPathPage Component', () => {
  test('Create a Snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <MyLearningPathPage userInfo={userInfo} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
