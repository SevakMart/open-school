import { create } from 'react-test-renderer';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import userEvent from '@testing-library/user-event';
import {
  INVALID_EMAIL_ERROR_MESSAGE, INVALID_PASSWORD_ERROR_MESSAGE,
  INVALID_FULL_NAME_ERROR_MESSAGE, FULL_NAME_TOO_SHORT, EMAIL_TOO_SHORT, PASSWORD_TOO_SHORT,
} from '../../../constants/Strings';
import SignUp from '../SignUp';

describe('Create tests for sign up form', () => {
  test('Create a snapshot test', () => {
    const component = create(<SignUp handleSignUpClicks={() => null} />);
    expect(component.toJSON()).toMatchSnapshot();
  });
  test('Test if the form disappears after clicking the close button', () => {
    render(<SignUp handleSignUpClicks={() => null} />);
    const closeButton = screen.queryByTestId('closeButton');
    const formComponent = screen.queryByRole('form');
    userEvent.click(closeButton as HTMLDivElement);
    expect(formComponent).not.toBeInTheDocument();
  });
  test('Test signUp form error messages:test1', () => {
    render(<SignUp handleSignUpClicks={() => null} />);
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
    render(<SignUp handleSignUpClicks={() => null} />);
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
    render(<SignUp handleSignUpClicks={() => null} />);
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
});
