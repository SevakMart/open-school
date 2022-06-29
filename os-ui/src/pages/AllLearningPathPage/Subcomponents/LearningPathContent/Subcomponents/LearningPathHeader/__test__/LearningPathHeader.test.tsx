import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../../../redux/Store';
import LearningPathHeader from '../LearningPathHeader';

const mockUseNavigate = jest.fn();
const mockUseLocation = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
  useLocation: () => mockUseLocation,
}));

describe('Create test cases for learningPathHeader Component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <LearningPathHeader handleChangeHeader={() => null} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Test when we are on all learning path page,the app will render as expected', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <LearningPathHeader handleChangeHeader={() => null} />
      </Provider>,
    );
    const sortingLabelElement = screen.queryByTestId('sorting');
    expect(sortingLabelElement).toBeInTheDocument();
  });
});
