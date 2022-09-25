import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import SignInDefault from '../Default';

describe('Create Test files for default signIn component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <SignInDefault
          handleSignIn={() => null}
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
