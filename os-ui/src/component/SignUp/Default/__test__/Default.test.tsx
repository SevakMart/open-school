import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import userEvent from '@testing-library/user-event';
import { Provider } from 'react-redux';
import { store } from '../../../../redux/Store';
import {
  INVALID_EMAIL_ERROR_MESSAGE, INVALID_PASSWORD_ERROR_MESSAGE,
  INVALID_FULL_NAME_ERROR_MESSAGE, FULL_NAME_TOO_SHORT, EMAIL_TOO_SHORT, PASSWORD_TOO_SHORT,
} from '../../../../constants/Strings';
import SignUpDefault from '../Default';

describe('Create tests for From component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <SignUpDefault switchToSignInForm={() => null} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Test signUp form error messages:test1', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <SignUpDefault switchToSignInForm={() => null} />
      </Provider>,
    );
    const fullNameInputField = screen.queryByPlaceholderText('Fill in first name');
    const emailInputField = screen.queryByPlaceholderText('ex: namesurname@gmail.com');
    const passwordInputField = screen.queryByPlaceholderText('Enter your password');
    const signUpButton = screen.queryByTestId('signUpButton');

    userEvent.type(fullNameInputField as HTMLInputElement, 'hello');
    userEvent.type(emailInputField as HTMLInputElement, 'hello');
    userEvent.type(passwordInputField as HTMLInputElement, 'hello');
    userEvent.click(signUpButton as HTMLButtonElement);

    const fullNameErrorMessage = screen.queryByText(FULL_NAME_TOO_SHORT);
    const emailErrorMessage = screen.queryByText(EMAIL_TOO_SHORT);
    const passwordErrorMessage = screen.queryByText(PASSWORD_TOO_SHORT);

    expect(fullNameErrorMessage).toBeInTheDocument();
    expect(emailErrorMessage).toBeInTheDocument();
    expect(passwordErrorMessage).toBeInTheDocument();
  });
  test('Test signUp form error messages:Show Invalid error messages', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <SignUpDefault switchToSignInForm={() => null} />
      </Provider>,
    );
    const fullNameInputField = screen.queryByPlaceholderText('Fill in first name');
    const emailInputField = screen.queryByPlaceholderText('ex: namesurname@gmail.com');
    const passwordInputField = screen.queryByPlaceholderText('Enter your password');
    const signUpButton = screen.queryByTestId('signUpButton');

    userEvent.type(fullNameInputField as HTMLInputElement, 'hello1527');
    userEvent.type(emailInputField as HTMLInputElement, 'hellos4578');
    userEvent.type(passwordInputField as HTMLInputElement, 'hellos12457');
    userEvent.click(signUpButton as HTMLButtonElement);

    const fullNameErrorMessage = screen.queryByText(INVALID_FULL_NAME_ERROR_MESSAGE);
    const emailErrorMessage = screen.queryByText(INVALID_EMAIL_ERROR_MESSAGE);
    const passwordErrorMessage = screen.queryByText(INVALID_PASSWORD_ERROR_MESSAGE);

    expect(fullNameErrorMessage).toBeInTheDocument();
    expect(emailErrorMessage).toBeInTheDocument();
    expect(passwordErrorMessage).toBeInTheDocument();
  });
  test('Test signUp form error messages: Create a valid full name only', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <SignUpDefault switchToSignInForm={() => null} />
      </Provider>,
    );
    const fullNameInputField = screen.queryByPlaceholderText('Fill in first name');
    const emailInputField = screen.queryByPlaceholderText('ex: namesurname@gmail.com');
    const passwordInputField = screen.queryByPlaceholderText('Enter your password');
    const signUpButton = screen.queryByTestId('signUpButton');

    userEvent.type(fullNameInputField as HTMLInputElement, 'hello world');
    userEvent.type(emailInputField as HTMLInputElement, 'hellos4578');
    userEvent.type(passwordInputField as HTMLInputElement, 'hellos12457');
    userEvent.click(signUpButton as HTMLButtonElement);

    const fullNameErrorMessage = screen.queryByTestId('fullnameErrorField');
    const emailErrorMessage = screen.queryByText(INVALID_EMAIL_ERROR_MESSAGE);
    const passwordErrorMessage = screen.queryByText(INVALID_PASSWORD_ERROR_MESSAGE);

    expect(fullNameErrorMessage).not.toBeInTheDocument();
    expect(emailErrorMessage).toBeInTheDocument();
    expect(passwordErrorMessage).toBeInTheDocument();
  });
  test('Test signUp form error messages: Create a valid form on submit', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <SignUpDefault switchToSignInForm={() => null} />
      </Provider>,
    );
    const fullNameInputField = screen.queryByPlaceholderText('Fill in first name');
    const emailInputField = screen.queryByPlaceholderText('ex: namesurname@gmail.com');
    const passwordInputField = screen.queryByPlaceholderText('Enter your password');
    const signUpButton = screen.queryByTestId('signUpButton');

    userEvent.type(fullNameInputField as HTMLInputElement, 'hello world');
    userEvent.type(emailInputField as HTMLInputElement, 'hello@hello.com');
    userEvent.type(passwordInputField as HTMLInputElement, 'HelloWorld93$');
    userEvent.click(signUpButton as HTMLButtonElement);

    const fullNameErrorMessage = screen.queryByTestId('fullnameErrorField');
    const emailErrorMessage = screen.queryByText(INVALID_EMAIL_ERROR_MESSAGE);
    const passwordErrorMessage = screen.queryByText(INVALID_PASSWORD_ERROR_MESSAGE);

    expect(fullNameErrorMessage).not.toBeInTheDocument();
    expect(emailErrorMessage).not.toBeInTheDocument();
    expect(passwordErrorMessage).not.toBeInTheDocument();
  });
});
