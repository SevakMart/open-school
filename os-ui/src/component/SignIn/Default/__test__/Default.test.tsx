import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import userEvent from '@testing-library/user-event';
import { Provider } from 'react-redux';
import { store } from '../../../../redux/Store';
import SignInDefault from '../Default';
import { INVALID_EMAIL_ERROR_MESSAGE, EMAIL_REQUIRED, PASSWORD_REQUIRED } from '../../../../constants/Strings';

describe('Create tests for sign in default form component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <SignInDefault handleSignIn={() => null} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Verify if the full name input field is absent in sign in form', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <SignInDefault handleSignIn={() => null} />
      </Provider>,
    );
    const fullNameInputField = screen.queryByPlaceholderText('Fill in first name');
    expect(fullNameInputField).toBeNull();
  });
  test('Test sign In form error message:absent email', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <SignInDefault handleSignIn={() => null} />
      </Provider>,
    );
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
    render(
      <Provider store={store}>
        <SignInDefault handleSignIn={() => null} />
      </Provider>,
    );
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
    render(
      <Provider store={store}>
        <SignInDefault handleSignIn={() => null} />
      </Provider>,
    );
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
    render(
      <Provider store={store}>
        <SignInDefault handleSignIn={() => null} />
      </Provider>,
    );
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
    render(
      <Provider store={store}>
        <SignInDefault handleSignIn={() => null} />
      </Provider>,
    );
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
    render(
      <Provider store={store}>
        <SignInDefault handleSignIn={() => null} />
      </Provider>,
    );
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
