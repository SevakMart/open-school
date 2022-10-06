import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import HomepageHeader from '../Header';

describe('Create tests for HomepageHeader', () => {
  test('Create snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <HomepageHeader />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
