import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../../../redux/Store';
import CourseSummaryItem from '../CourseSummaryItem';

describe('Create test cases for CourseSummaryItem', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <CourseSummaryItem title="Rating" value={5} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
