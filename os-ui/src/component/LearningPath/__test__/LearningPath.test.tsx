import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import { store } from '../../../redux/Store';
import LearningPath from '../LearningPath';

const props = {
  id: 1,
  title: 'React',
  rating: 5.5,
  difficulty: 'Medium',
  keywords: ['Testing', 'database', 'memory'],
};

describe('Create test cases to learning path component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <LearningPath
          courseInfo={props}
        />
      </Provider>,
      { wrapper: BrowserRouter },
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Check if the content is as expected', () => {
    render(
      <Provider store={store}>
        <LearningPath
          courseInfo={props}
        />
      </Provider>,
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
      <Provider store={store}>
        <LearningPath
          courseInfo={props}
        />
      </Provider>,
      { wrapper: BrowserRouter },
    );
    const remainingKeywordNumberElement = screen.queryByTestId('remainingKeywordNumber');
    expect(remainingKeywordNumberElement).not.toBeInTheDocument();
  });
});
