import { render, screen } from '@testing-library/react';
import CompletedCourse from '../CompledtedCourse';

describe('Create test cases for the Completed course component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <CompletedCourse
        title="React js"
        courseStatus="COMPLETED"
        grade={90}
      />,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Check if the content is as expected', () => {
    render(
      <CompletedCourse
        title="React js"
        courseStatus="COMPLETED"
        grade={90}
      />,
    );
    const courseTitleElement = screen.queryByTestId('React js');
    const statusContentElement = screen.queryByTestId('COMPLETED');
    const gradeContentElement = screen.queryByTestId(90);

    expect(courseTitleElement).toBeInTheDocument();
    expect(courseTitleElement).toHaveTextContent('React js');
    expect(statusContentElement).toBeInTheDocument();
    expect(statusContentElement).toHaveTextContent('COMPLETED');
    expect(gradeContentElement).toBeInTheDocument();
    expect(gradeContentElement).toHaveTextContent('90/100');
  });
});
