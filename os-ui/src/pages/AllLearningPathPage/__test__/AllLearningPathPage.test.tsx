import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import AllLearningPathPage from '../AllLearningPathPage';

const mockUseNavigate = jest.fn();
const mockUseLocation = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
  useLocation: () => mockUseLocation,
}));

describe('Create test cases for AllLearningPathPage component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <AllLearningPathPage />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
