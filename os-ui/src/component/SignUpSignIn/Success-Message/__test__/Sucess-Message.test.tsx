import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../redux/Store';
import SuccessMessage from '../Success-Message';

jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));

describe('Create test case for Successful message component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <SuccessMessage
          message="You have successfuly registered"
          isSignUpSuccessfulRegistration
          isResendVerificationEmailMessage={false}
          isResetPasswordSuccessfulMessage={false}
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
