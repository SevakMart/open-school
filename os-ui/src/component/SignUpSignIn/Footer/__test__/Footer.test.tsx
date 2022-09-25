import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../../redux/Store';
import Footer from '../Footer';

describe('Create unit tests for Footer component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <Footer
          mainText="This is main text"
          buttonType="signIn"
          buttonText="Sign In"
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
