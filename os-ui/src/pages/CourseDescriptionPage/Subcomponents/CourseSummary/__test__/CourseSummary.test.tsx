import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import CourseSummary from '../CourseSummary';

describe('Create test cases for course description page', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <CourseSummary
          rating={4.5}
          enrolled={2}
          level="Advanced"
          language="French"
          duration={360}
          enrollInCourse={() => null}
          isEnrolled={false}
          courseId={2}
          userIdAndToken={{ id: 2, token: 'jksndfkjnskfk' }}
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
