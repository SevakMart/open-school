import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import SavedCoursesContent from '../SavedCoursesContent';
import userService from '../../../../../services/userService';

jest.mock('react-i18next', () => ({
  useTranslation: () => ({
    t: () => 'No data to display',
  }),
}));

const data = {
  content: [
    {
      title: 'JavaScript',
      rating: 5.5,
      difficulty: 'Medium',
      keywords: ['Testing', 'database', 'memory'],
    },
    {
      title: 'React',
      rating: 5.6,
      difficulty: 'Medium',
      keywords: ['Testing', 'database', 'memory'],
    },
  ],
};

const mockUseNavigate = jest.fn();
const mockUseLocation = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
  useLocation: () => mockUseLocation,
}));

describe('Create test cases for SavedCourseContent component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <SavedCoursesContent />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Test if component renders as expected when we do not have saved courses', async () => {
    expect.hasAssertions();
    jest.spyOn(userService, 'getUserSavedCourses').mockResolvedValue({ content: [] });
    render(
      <Provider store={store}>
        <SavedCoursesContent />
      </Provider>,
    );
    const emptyDataheaderElement = await screen.findByTestId('Empty data Message');
    expect(emptyDataheaderElement).toBeInTheDocument();
    expect(emptyDataheaderElement).toHaveTextContent('No data to display');
  });
  test('Test if component renders as expected when we have saved courses', async () => {
    expect.hasAssertions();
    jest.spyOn(userService, 'getUserSavedCourses').mockResolvedValue(data);
    render(
      <Provider store={store}>
        <SavedCoursesContent />
      </Provider>,
    );
    const emptyDataheaderElement = await screen.findByTestId('Empty data Message');
    expect(emptyDataheaderElement).not.toBeInTheDocument();
  });
});
