import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../redux/Store';
import SignUp from '../SignUp';

describe('Create tests for sign up form', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <SignUp />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
