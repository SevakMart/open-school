import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import CourseDescriptionPage from '../CourseDescriptionPage';

describe('Create test cases for course description page', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <CourseDescriptionPage />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
