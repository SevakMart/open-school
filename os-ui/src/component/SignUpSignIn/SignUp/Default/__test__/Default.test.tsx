import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import SignUpDefault from '../Default';

describe('Create test case for Default Sign up component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <SignUpDefault />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
