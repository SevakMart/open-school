import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import CourseContentHeader from '../CourseContentHeader';

describe('Create test cases for CourseContentHeader', () => {
  test('Make a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <CourseContentHeader handleChangeHeader={jest.fn()} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
