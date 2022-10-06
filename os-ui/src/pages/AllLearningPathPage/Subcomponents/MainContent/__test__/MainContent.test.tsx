import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import MainContent from '../MainContent';

describe('Create test cases for CourseContentHeader', () => {
  test('Make a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <MainContent />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
