import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import CourseDescriptionPage from '../CourseDescriptionPage';

describe('Create test cases for course description page', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(<Provider store={store}>
      <CourseDescriptionPage />
      {/* eslint-disable-next-line */}
      </Provider>,{ wrapper: BrowserRouter });
    expect(asFragment()).toMatchSnapshot();
  });
});
