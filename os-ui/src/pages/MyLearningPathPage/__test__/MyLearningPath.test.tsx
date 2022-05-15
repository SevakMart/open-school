import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import userService from '../../../services/userService';
import MyLearningPathPage from '../MyLearningPathPage';

const userAllCourseData = [
  {
    title: 'React js',
    courseStatus: 'IN_PROGRESS',
    percentage: 47,
    remainingTime: 5400000,
    dueDate: '2022-05-07',
  },
  {
    title: 'Node js',
    courseStatus: 'COMPLETED',
    grade: 90,
  },
];
const suggestedCourse = [
  {
    title: 'React',
    rating: 5.5,
    difficulty: 'Medium',
    keywords: ['Testing', 'database', 'memory'],
  },
  {
    title: 'Node',
    rating: 5.5,
    difficulty: 'Hard',
    keywords: ['Testing', 'database', 'memory'],
  },
  {
    title: 'JS',
    rating: 5.5,
    difficulty: 'Easy',
    keywords: ['Testing', 'database', 'memory'],
  },
  {
    title: 'TS',
    rating: 5.5,
    difficulty: 'Medium',
    keywords: ['Testing', 'database', 'memory'],
  },
];

describe('Create test case for Learning path component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(<Provider store={store}><MyLearningPathPage /></Provider>);
    expect(asFragment()).toMatchSnapshot();
  });
  test('Check if the content is displayed as expected', async () => {
    expect.hasAssertions();
    jest.spyOn(userService, 'getUserCourses').mockResolvedValue(userAllCourseData);
    jest.spyOn(userService, 'getSuggestedCourses').mockResolvedValue(suggestedCourse);
    render(<Provider store={store}><MyLearningPathPage /></Provider>);

    const courseTitleElement1 = await screen.findByTestId('React js');
    const statusContentElement1 = await screen.findByTestId('IN_PROGRESS');
    const courseTitleElement2 = await screen.findByTestId('Node js');
    const statusContentElement2 = await screen.findByTestId('COMPLETED');
    const learningPathElement = await screen.findByTestId('React');
    const remainingKeywordNumberElement = await screen.findByTestId('remainingKeywordNumber of React');

    expect(courseTitleElement1).toBeInTheDocument();
    expect(courseTitleElement1).toHaveTextContent('React js');
    expect(courseTitleElement2).toBeInTheDocument();
    expect(courseTitleElement2).toHaveTextContent('Node js');
    expect(statusContentElement1).toBeInTheDocument();
    expect(statusContentElement1).toHaveTextContent('IN_PROGRESS');
    expect(statusContentElement2).toBeInTheDocument();
    expect(statusContentElement2).toHaveTextContent('COMPLETED');
    expect(learningPathElement).toBeInTheDocument();
    expect(learningPathElement).toHaveTextContent('React');
    expect(remainingKeywordNumberElement).toBeInTheDocument();
    expect(remainingKeywordNumberElement).toHaveTextContent('+1');
  });
  test('Check if the no course component is displayed when the yser does not have courses yet', async () => {
    expect.hasAssertions();
    jest.spyOn(userService, 'getUserCourses').mockResolvedValue([]);
    jest.spyOn(userService, 'getSuggestedCourses').mockResolvedValue(suggestedCourse);
    render(<Provider store={store}><MyLearningPathPage /></Provider>);

    const noCourseParagraphElement = await screen.findByTestId('No courses yet');

    expect(noCourseParagraphElement).toBeInTheDocument();
    expect(noCourseParagraphElement).toHaveTextContent('No Courses Yet');
  });
});
