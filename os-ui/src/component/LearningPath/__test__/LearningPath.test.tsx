import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
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
        title={props.title}
        rating={props.rating}
        difficulty={props.difficulty}
        keywords={props.keywords}
      />,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Check if the content is as expected', () => {
    render(
      <LearningPath
        title={props.title}
        rating={props.rating}
        difficulty={props.difficulty}
        keywords={props.keywords}
      />,
    );
    const difficultyContentElement = screen.queryByTestId('Medium');
    const learningPathElement = screen.queryByTestId('React');
    const remainingKeywordNumberElement = screen.queryByTestId('remainingKeywordNumber');

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
        title={props.title}
        rating={props.rating}
        difficulty={props.difficulty}
        keywords={['Testing', 'database']}
      />,
    );
    const remainingKeywordNumberElement = screen.queryByTestId('remainingKeywordNumber');
    expect(remainingKeywordNumberElement).not.toBeInTheDocument();
  });
});
