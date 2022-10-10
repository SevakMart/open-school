import { render, screen } from '@testing-library/react';
import InProgressCourse from '../InProgressCourse';

jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));

describe('Create test cases for InProgress component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <InProgressCourse
        title="React js"
        courseStatus="In_Progress"
        percentage={47}
        remainingTime={540000}
        dueDate="2022-05-07"
      />,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Check if the content is as expected', () => {
    render(
      <InProgressCourse
        title="React js"
        courseStatus="In_Progress"
        percentage={47}
        remainingTime={5400000}
        dueDate="2022-05-07"
      />,
    );
    const courseTitleElement = screen.queryByTestId('React js');
    const statusContentElement = screen.queryByTestId('In_Progress');
    const percentageContentElement = screen.queryByTestId(47);
    const remainingTimeContentElement = screen.queryByTestId(5400000);
    const dueDateContentElement = screen.queryByTestId('2022-05-07');

    expect(courseTitleElement).toBeInTheDocument();
    expect(courseTitleElement).toHaveTextContent('React js');
    expect(statusContentElement).toBeInTheDocument();
    expect(statusContentElement).toHaveTextContent('In_Progress');
    expect(percentageContentElement).toBeInTheDocument();
    expect(percentageContentElement).toHaveTextContent('47%');
    expect(remainingTimeContentElement).toBeInTheDocument();
    expect(remainingTimeContentElement).toHaveTextContent('1h 30m');
    expect(dueDateContentElement).toBeInTheDocument();
    expect(dueDateContentElement).toHaveTextContent('2022-05-07');
  });
});
