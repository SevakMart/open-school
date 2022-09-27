import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { BrowserRouter } from 'react-router-dom';
import LearningPath from '../LearningPath';

const props = {
  title: 'React',
  rating: 5.5,
  difficulty: 'Medium',
  keywords: ['Testing', 'database', 'memory'],
};

describe('Create test cases to learning path component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <LearningPath
        courseInfo={props}
        isUserSavedCourse={false}
      />,
      { wrapper: BrowserRouter },
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Check if the content is as expected', () => {
    render(
      <LearningPath
        courseInfo={props}
        isUserSavedCourse={false}
      />,
      { wrapper: BrowserRouter },
    );
    const difficultyContentElement = screen.queryByTestId('Medium');
    const learningPathElement = screen.queryByTestId('React');
    const remainingKeywordNumberElement = screen.queryByTestId('remainingKeywordNumber of React');

    expect(difficultyContentElement).toHaveTextContent('Medium');
    expect(learningPathElement).toHaveTextContent('React');
    expect(remainingKeywordNumberElement).toHaveTextContent('+1');
    expect(difficultyContentElement).toBeInTheDocument();
    expect(learningPathElement).toBeInTheDocument();
    expect(remainingKeywordNumberElement).toBeInTheDocument();
  });
  test('Check if no keyword number is shown if the number of keywords were less than 3', () => {
    render(
      <LearningPath
        courseInfo={props}
        isUserSavedCourse={false}
      />,
      { wrapper: BrowserRouter },
    );
    const remainingKeywordNumberElement = screen.queryByTestId('remainingKeywordNumber');
    expect(remainingKeywordNumberElement).not.toBeInTheDocument();
  });
});
