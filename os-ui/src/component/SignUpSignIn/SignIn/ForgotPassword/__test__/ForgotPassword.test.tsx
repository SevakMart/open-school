import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import ForgotPassword from '../ForgotPassword';

describe('Create test cases for ForgotPassword component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <ForgotPassword />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
