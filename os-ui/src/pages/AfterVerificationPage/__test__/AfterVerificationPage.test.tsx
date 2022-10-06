import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import AfterVerificationPage from '../AfterVerificationPage';

describe('Create test cases for AfterVerification page', () => {
  test('Make a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <AfterVerificationPage />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
