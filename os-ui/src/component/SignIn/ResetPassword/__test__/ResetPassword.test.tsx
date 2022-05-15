import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import userEvent from '@testing-library/user-event';
import { Provider } from 'react-redux';
import { store } from '../../../../redux/Store';
import {
  INVALID_PASSWORD_ERROR_MESSAGE, INVALID_TOKEN,
  TOKEN_REQUIRED, PASSWORD_REQUIRED, PASSWORDS_MISMATCH,
} from '../../../../constants/Strings';
import ResetPassword from '../ResetPassword';
import authService from '../../../../services/authService';

const successData = {
  data: {
    message: 'You have successfuly reset your password',
  },
  status: 200,
};
const failedData = {
  data: {
    message: 'Invalid token',
  },
  status: 400,
};
const forgotPasswordResponse = {
  data: {
    message: 'You have successfully resend the request',
  },
  status: 200,
};

describe('Create test cases for ResetPassword Component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <ResetPassword
          email="john@john.com"
          returnToSignInForm={() => null}
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Create a test case where reseting password without a token', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <ResetPassword
          email="john@john.com"
          returnToSignInForm={() => null}
        />
      </Provider>,
    );

    const passwordInputField = screen.queryByPlaceholderText('Enter the new password');
    const confirmPasswordInputField = screen.queryByPlaceholderText('Confirm password');

    userEvent.type((passwordInputField as HTMLInputElement), 'Helloworld11$');
    userEvent.type((confirmPasswordInputField as HTMLInputElement), 'Helloworld11$');

    const resetPasswordButton = screen.queryByTestId('resetPasswordButton');
    userEvent.click((resetPasswordButton as HTMLButtonElement));

    const tokenErrorMessageElement = screen.queryByTestId('tokenErrorMessage');

    expect(tokenErrorMessageElement).toBeInTheDocument();
    expect(tokenErrorMessageElement).toHaveTextContent(TOKEN_REQUIRED);
  });
  test('Create a test case where we insert an invalid token', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <ResetPassword
          email="john@john.com"
          returnToSignInForm={() => null}
        />
      </Provider>,
    );
    const tokenInputField = screen.queryByPlaceholderText('Enter the 4 digits code that you received on your email');
    const passwordInputField = screen.queryByPlaceholderText('Enter the new password');
    const confirmPasswordInputField = screen.queryByPlaceholderText('Confirm password');

    userEvent.type((tokenInputField as HTMLInputElement), '123');
    userEvent.type((passwordInputField as HTMLInputElement), 'Helloworld11$');
    userEvent.type((confirmPasswordInputField as HTMLInputElement), 'Helloworld11$');

    const resetPasswordButton = screen.queryByTestId('resetPasswordButton');
    userEvent.click((resetPasswordButton as HTMLButtonElement));

    const tokenErrorMessageElement = screen.queryByTestId('tokenErrorMessage');

    expect(tokenErrorMessageElement).toBeInTheDocument();
    expect(tokenErrorMessageElement).toHaveTextContent(INVALID_TOKEN);
  });
  test('Create a test case where we reset the password without inserting a password', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <ResetPassword
          email="john@john.com"
          returnToSignInForm={() => null}
        />
      </Provider>,
    );
    const tokenInputField = screen.queryByPlaceholderText('Enter the 4 digits code that you received on your email');
    userEvent.type((tokenInputField as HTMLInputElement), '1234');

    const resetPasswordButton = screen.queryByTestId('resetPasswordButton');
    userEvent.click((resetPasswordButton as HTMLButtonElement));

    const newPasswordErrorMessageElement = screen.queryByTestId('newPasswordErrorMessage');
    const confirmedPasswordErrorMessageElement = screen.queryByTestId('confirmedPasswordErrorMessage');

    expect(newPasswordErrorMessageElement).toBeInTheDocument();
    expect(confirmedPasswordErrorMessageElement).toBeInTheDocument();
    expect(newPasswordErrorMessageElement).toHaveTextContent(PASSWORD_REQUIRED);
    expect(confirmedPasswordErrorMessageElement).toHaveTextContent(PASSWORD_REQUIRED);
  });
  test('Reset password without inserting a field in the form', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <ResetPassword
          email="john@john.com"
          returnToSignInForm={() => null}
        />
      </Provider>,
    );
    const resetPasswordButton = screen.queryByTestId('resetPasswordButton');
    userEvent.click((resetPasswordButton as HTMLButtonElement));

    const tokenErrorMessageElement = screen.queryByTestId('tokenErrorMessage');
    const newPasswordErrorMessageElement = screen.queryByTestId('newPasswordErrorMessage');
    const confirmedPasswordErrorMessageElement = screen.queryByTestId('confirmedPasswordErrorMessage');

    expect(tokenErrorMessageElement).toBeInTheDocument();
    expect(newPasswordErrorMessageElement).toBeInTheDocument();
    expect(confirmedPasswordErrorMessageElement).toBeInTheDocument();
    expect(tokenErrorMessageElement).toHaveTextContent(TOKEN_REQUIRED);
    expect(newPasswordErrorMessageElement).toHaveTextContent(PASSWORD_REQUIRED);
    expect(confirmedPasswordErrorMessageElement).toHaveTextContent(PASSWORD_REQUIRED);
  });
  test('Create a mismatch between password and confirm password fields', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <ResetPassword
          email="john@john.com"
          returnToSignInForm={() => null}
        />
      </Provider>,
    );
    const tokenInputField = screen.queryByPlaceholderText('Enter the 4 digits code that you received on your email');
    const passwordInputField = screen.queryByPlaceholderText('Enter the new password');
    const confirmPasswordInputField = screen.queryByPlaceholderText('Confirm password');

    userEvent.type((tokenInputField as HTMLInputElement), '1234');
    userEvent.type((passwordInputField as HTMLInputElement), 'Helloworld11$');
    userEvent.type((confirmPasswordInputField as HTMLInputElement), 'Helloworld11');

    const resetPasswordButton = screen.queryByTestId('resetPasswordButton');
    userEvent.click((resetPasswordButton as HTMLButtonElement));

    const tokenErrorMessageElement = screen.queryByTestId('tokenErrorMessage');
    const newPasswordErrorMessageElement = screen.queryByTestId('newPasswordErrorMessage');
    const confirmedPasswordErrorMessageElement = screen.queryByTestId('confirmedPasswordErrorMessage');

    expect(tokenErrorMessageElement).not.toBeInTheDocument();
    expect(newPasswordErrorMessageElement).not.toBeInTheDocument();
    expect(confirmedPasswordErrorMessageElement).toBeInTheDocument();
    expect(confirmedPasswordErrorMessageElement).toHaveTextContent(PASSWORDS_MISMATCH);
  });
  test('Create a test case with invalid token and password', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <ResetPassword
          email="john@john.com"
          returnToSignInForm={() => null}
        />
      </Provider>,
    );
    const tokenInputField = screen.queryByPlaceholderText('Enter the 4 digits code that you received on your email');
    const passwordInputField = screen.queryByPlaceholderText('Enter the new password');
    const confirmPasswordInputField = screen.queryByPlaceholderText('Confirm password');

    userEvent.type((tokenInputField as HTMLInputElement), '123');
    userEvent.type((passwordInputField as HTMLInputElement), 'Helloworld11');
    userEvent.type((confirmPasswordInputField as HTMLInputElement), 'Helloworld11');

    const resetPasswordButton = screen.queryByTestId('resetPasswordButton');
    userEvent.click((resetPasswordButton as HTMLButtonElement));

    const tokenErrorMessageElement = screen.queryByTestId('tokenErrorMessage');
    const newPasswordErrorMessageElement = screen.queryByTestId('newPasswordErrorMessage');
    const confirmedPasswordErrorMessageElement = screen.queryByTestId('confirmedPasswordErrorMessage');

    expect(tokenErrorMessageElement).toBeInTheDocument();
    expect(newPasswordErrorMessageElement).toBeInTheDocument();
    expect(confirmedPasswordErrorMessageElement).not.toBeInTheDocument();
    expect(tokenErrorMessageElement).toHaveTextContent(INVALID_TOKEN);
    expect(newPasswordErrorMessageElement).toHaveTextContent(INVALID_PASSWORD_ERROR_MESSAGE);
  });
  test('Create a test case that shows a successful password reset', async () => {
    expect.hasAssertions();
    jest.spyOn(authService, 'sendResetPasswordRequest').mockResolvedValue(successData);

    render(
      <Provider store={store}>
        <ResetPassword
          email="john@john.com"
          returnToSignInForm={() => null}
        />
      </Provider>,
    );
    const tokenInputField = screen.queryByPlaceholderText('Enter the 4 digits code that you received on your email');
    const passwordInputField = screen.queryByPlaceholderText('Enter the new password');
    const confirmPasswordInputField = screen.queryByPlaceholderText('Confirm password');

    userEvent.type((tokenInputField as HTMLInputElement), '1234');
    userEvent.type((passwordInputField as HTMLInputElement), 'Helloworld11$');
    userEvent.type((confirmPasswordInputField as HTMLInputElement), 'Helloworld11$');

    const resetPasswordButton = screen.queryByTestId('resetPasswordButton');
    userEvent.click((resetPasswordButton as HTMLButtonElement));

    const successResetPasswordMessageElement = await screen.findByTestId('successResetPasswordMessage');

    expect(successResetPasswordMessageElement).toBeInTheDocument();
    expect(successResetPasswordMessageElement).toHaveTextContent(successData.data.message);
  });

  test('Create a test case that shows a failed password reset', async () => {
    expect.hasAssertions();
    jest.spyOn(authService, 'sendResetPasswordRequest').mockResolvedValue(failedData);

    render(
      <Provider store={store}>
        <ResetPassword
          email="john@john.com"
          returnToSignInForm={() => null}
        />
      </Provider>,
    );
    const tokenInputField = screen.queryByPlaceholderText('Enter the 4 digits code that you received on your email');
    const passwordInputField = screen.queryByPlaceholderText('Enter the new password');
    const confirmPasswordInputField = screen.queryByPlaceholderText('Confirm password');

    userEvent.type((tokenInputField as HTMLInputElement), '1234');
    userEvent.type((passwordInputField as HTMLInputElement), 'Helloworld11$');
    userEvent.type((confirmPasswordInputField as HTMLInputElement), 'Helloworld11$');

    const resetPasswordButton = screen.queryByTestId('resetPasswordButton');
    userEvent.click((resetPasswordButton as HTMLButtonElement));

    const tokenErrorMessageElement = await screen.findByTestId('tokenErrorMessage');

    expect(tokenErrorMessageElement).toHaveTextContent(failedData.data.message);
  });
  test('Show a message after clicking on resend email', async () => {
    expect.hasAssertions();
    jest.spyOn(authService, 'sendForgotPasswordRequest').mockResolvedValue(forgotPasswordResponse);
    render(
      <Provider store={store}>
        <ResetPassword
          email="john@john.com"
          returnToSignInForm={() => null}
        />
      </Provider>,
    );
    const resendEmailButton = screen.queryByTestId('resendEmailButton');
    userEvent.click((resendEmailButton as HTMLButtonElement));

    const successForgotPasswordMessageElement = await screen.findByTestId('successForgotPasswordMessage');

    expect(successForgotPasswordMessageElement).toBeInTheDocument();
    expect(successForgotPasswordMessageElement)
      .toHaveTextContent(forgotPasswordResponse.data.message);
  });
});
