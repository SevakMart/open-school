import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import * as fetchUserCourses from '../../../services/getUserCourses';
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

describe('Create test case for Learning path component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(<Provider store={store}><MyLearningPathPage /></Provider>);
    expect(asFragment()).toMatchSnapshot();
  });
  /* test('Check if clicking in_progress will show only in progress courses', async () => {
    expect.hasAssertions();
    const spy = jest.spyOn(fetchUserCourses, 'getUserCourses');
    spy.mockResolvedValue(userAllCourseData);
    render(<Provider store={store}><MyLearningPathPage /></Provider>);
    const inProgressNavItem = screen.queryByTestId('InProgress');
    spy.mockImplementation(()=>userAllCourseData[0]);
    userEvent.click(inProgressNavItem as HTMLParagraphElement);

    const courseTitleElement1 = await screen.findByTestId('React js');
    const courseTitleElement2 = await screen.findByTestId('Node js');

    expect(courseTitleElement1).toBeInTheDocument();
    expect(courseTitleElement2).not.toBeInTheDocument();
  }); */
});
