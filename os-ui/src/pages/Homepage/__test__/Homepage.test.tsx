import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import Homepage from '../Homepage';

const userInfo = {
  token: '123',
  id: 1,
};

describe('Create several unit tests for Homepage Component', () => {
  test('Create a Snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <Homepage userInfo={userInfo} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
