import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import HomepageMentors from '../Mentors';

describe('Create test cases for homepage mentors list', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <HomepageMentors />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
