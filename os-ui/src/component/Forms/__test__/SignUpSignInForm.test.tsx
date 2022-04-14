import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import userEvent from '@testing-library/user-event';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import Form from '../SignUpSignInForm';
import {
  INVALID_EMAIL_ERROR_MESSAGE, INVALID_PASSWORD_ERROR_MESSAGE,
  INVALID_FULL_NAME_ERROR_MESSAGE, FULL_NAME_TOO_SHORT, EMAIL_TOO_SHORT, PASSWORD_TOO_SHORT,
  EMAIL_REQUIRED, PASSWORD_REQUIRED,
} from '../../../constants/Strings';

describe('Create tests for From component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(<Provider store={store}><Form formType="signUp" /></Provider>);
    expect(asFragment()).toMatchSnapshot();
  });
  test('Test signUp form error messages:test1', () => {
    expect.hasAssertions();
    render(<Provider store={store}><Form formType="signUp" /></Provider>);
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
    render(<Provider store={store}><Form formType="signUp" /></Provider>);
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
    render(<Provider store={store}><Form formType="signUp" /></Provider>);
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
    render(<Provider store={store}><Form formType="signUp" /></Provider>);
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
describe('Create tests for sign in form component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(<Provider store={store}><Form formType="signIn" /></Provider>);
    expect(asFragment()).toMatchSnapshot();
  });
  test('Verify if the full name input field is absent in sign in form', () => {
    expect.hasAssertions();
    render(<Provider store={store}><Form formType="signIn" /></Provider>);
    const fullNameInputField = screen.queryByPlaceholderText('Fill in first name');
    expect(fullNameInputField).toBeNull();
  });
  test('Test sign In form error message:absent email', () => {
    expect.hasAssertions();
    render(<Provider store={store}><Form formType="signIn" /></Provider>);
    const passwordInputField = screen.queryByPlaceholderText('Enter your password');
    const signInButton = screen.queryByTestId('signInButton');

    userEvent.type(passwordInputField as HTMLInputElement, 'hello');
    userEvent.click(signInButton as HTMLButtonElement);

    const emailErrorMessage = screen.queryByTestId('emailErrorField');
    expect(emailErrorMessage).toBeInTheDocument();
    expect(emailErrorMessage).toHaveTextContent(EMAIL_REQUIRED);
  });
  test('Test sign In form error message:absent password', () => {
    expect.hasAssertions();
    render(<Provider store={store}><Form formType="signIn" /></Provider>);
    const emailInputField = screen.queryByPlaceholderText('ex: namesurname@gmail.com');
    const signInButton = screen.queryByTestId('signInButton');

    userEvent.type(emailInputField as HTMLInputElement, 'hello@hello.com');
    userEvent.click(signInButton as HTMLButtonElement);

    const passwordErrorMessage = screen.queryByTestId('passwordErrorField');
    expect(passwordErrorMessage).toBeInTheDocument();
    expect(passwordErrorMessage).toHaveTextContent(PASSWORD_REQUIRED);
  });
  test('Test sign In form error message:absent email & password', () => {
    expect.hasAssertions();
    render(<Provider store={store}><Form formType="signIn" /></Provider>);
    const signInButton = screen.queryByTestId('signInButton');

    userEvent.click(signInButton as HTMLButtonElement);

    const passwordErrorMessage = screen.queryByTestId('passwordErrorField');
    const emailErrorMessage = screen.queryByTestId('emailErrorField');

    expect(emailErrorMessage).toBeInTheDocument();
    expect(emailErrorMessage).toHaveTextContent(EMAIL_REQUIRED);
    expect(passwordErrorMessage).toBeInTheDocument();
    expect(passwordErrorMessage).toHaveTextContent(PASSWORD_REQUIRED);
  });
  test('Test sign In form error message:invalid email', () => {
    expect.hasAssertions();
    render(<Provider store={store}><Form formType="signIn" /></Provider>);
    const emailInputField = screen.queryByPlaceholderText('ex: namesurname@gmail.com');
    const passwordInputField = screen.queryByPlaceholderText('Enter your password');
    const signInButton = screen.queryByTestId('signInButton');

    userEvent.type(emailInputField as HTMLInputElement, 'hello');
    userEvent.type(passwordInputField as HTMLInputElement, 'hello');
    userEvent.click(signInButton as HTMLButtonElement);

    const emailErrorMessage = screen.queryByTestId('emailErrorField');
    expect(emailErrorMessage).toBeInTheDocument();
    expect(emailErrorMessage).toHaveTextContent(INVALID_EMAIL_ERROR_MESSAGE);
  });
  test('Test sign In form error message:invalid email & absent password', () => {
    expect.hasAssertions();
    render(<Provider store={store}><Form formType="signIn" /></Provider>);
    const emailInputField = screen.queryByPlaceholderText('ex: namesurname@gmail.com');
    const signInButton = screen.queryByTestId('signInButton');

    userEvent.type(emailInputField as HTMLInputElement, 'hello');
    userEvent.click(signInButton as HTMLButtonElement);

    const emailErrorMessage = screen.queryByTestId('emailErrorField');
    const passwordErrorMessage = screen.queryByTestId('passwordErrorField');

    expect(emailErrorMessage).toBeInTheDocument();
    expect(emailErrorMessage).toHaveTextContent(INVALID_EMAIL_ERROR_MESSAGE);
    expect(passwordErrorMessage).toBeInTheDocument();
    expect(passwordErrorMessage).toHaveTextContent(PASSWORD_REQUIRED);
  });

  test('Test sign In form error message:valid email & password', () => {
    expect.hasAssertions();
    render(<Provider store={store}><Form formType="signIn" /></Provider>);
    const emailInputField = screen.queryByPlaceholderText('ex: namesurname@gmail.com');
    const passwordInputField = screen.queryByPlaceholderText('Enter your password');
    const signInButton = screen.queryByTestId('signInButton');

    userEvent.type(emailInputField as HTMLInputElement, 'hello@hello.com');
    userEvent.type(passwordInputField as HTMLInputElement, 'hello');
    userEvent.click(signInButton as HTMLButtonElement);

    const passwordErrorMessage = screen.queryByTestId('passwordErrorField');
    const emailErrorMessage = screen.queryByTestId('emailErrorField');

    expect(emailErrorMessage).not.toBeInTheDocument();
    expect(passwordErrorMessage).not.toBeInTheDocument();
  });
});
