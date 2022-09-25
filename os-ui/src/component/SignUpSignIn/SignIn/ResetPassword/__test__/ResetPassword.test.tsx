import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import ResetPassword from '../ResetPassword';

describe('Create test cases for ResetPassword Component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <ResetPassword />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
