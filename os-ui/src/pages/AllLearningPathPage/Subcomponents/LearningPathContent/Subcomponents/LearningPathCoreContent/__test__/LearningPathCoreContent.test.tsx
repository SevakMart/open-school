import { render, screen } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
import { store } from '../../../../../../../redux/Store';
import courseService from '../../../../../../../services/courseService';
import userService from '../../../../../../../services/userService';
import LearningPathCoreContent from '../LearningPathCoreContent';

/* jest.mock('react-i18next', () => ({
  useTranslation: () => ({
    t: () => 'No data to display',
  }),
}));

jest.mock('redux-state-sync', () => ({
  createStateSyncMiddleware:
    () => () => (next: (action: PayloadAction) => void) => (action: PayloadAction) => next(action),
  initMessageListener: () => jest.fn(),
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
    {
      title: 'Node JS',
      rating: 6.2,
      difficulty: 'Advanced',
      keywords: ['Testing', 'database', 'memory'],
    },
    {
      title: 'Next JS',
      rating: 5,
      difficulty: 'Easy',
      keywords: ['Testing', 'database', 'memory'],
    },
  ],
};
describe('Create test cases for learningPathCoreComponent component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(<Provider store={store}>
      <LearningPathCoreContent />
      {/* eslint-disable-next-line */}
    </Provider>, { wrapper: BrowserRouter });
    expect(asFragment()).toMatchSnapshot();
  });
  test('Test component when we have an empty data array', async () => {
    expect.hasAssertions();
    jest.spyOn(courseService, 'getSearchedCourses').mockResolvedValue({ content: [] });
    jest.spyOn(userService, 'getUserSavedCourses').mockResolvedValue({ content: [] });
    render(<Provider store={store}>
      <LearningPathCoreContent />
      {/* eslint-disable-next-line */}
    </Provider>, { wrapper: BrowserRouter });
    const ErrorMessageHeaderElement = await screen.findByTestId('Error Message');
    expect(ErrorMessageHeaderElement).toBeInTheDocument();
    expect(ErrorMessageHeaderElement).toHaveTextContent('No data to display');
  });
  test('Test component when we have a non empty data result', async () => {
    expect.hasAssertions();
    jest.spyOn(courseService, 'getSearchedCourses').mockResolvedValue(data);
    jest.spyOn(userService, 'getUserSavedCourses').mockResolvedValue({ content: [] });
    render(<Provider store={store}>
      <LearningPathCoreContent />
      {/* eslint-disable-next-line */}
    </Provider>, { wrapper: BrowserRouter });
    const ErrorMessageHeaderElement = await screen.findByTestId('Error Message');
    const learningPathTitleElement = await screen.findByTestId('React');

    expect(ErrorMessageHeaderElement).not.toBeInTheDocument();
    expect(learningPathTitleElement).toBeInTheDocument();
    expect(learningPathTitleElement).toHaveTextContent('React');
  });
});
*/
