import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../../redux/Store';
import Header from '../Header';

describe('Create unit tests for Header component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <Header
          mainTitle="This is main Title"
          shouldRemoveIconContent={false}
          isForgotPasswordContent={false}
          isVerificationContent={false}
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
