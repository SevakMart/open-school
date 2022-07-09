import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import userEvent from '@testing-library/user-event';
import { Provider } from 'react-redux';
import { store } from '../../../../redux/Store';
import {
  EMAIL_REQUIRED, EMAIL_TOO_LONG, INVALID_EMAIL_ERROR_MESSAGE,
  PASSWORD_REQUIRED, INVALID_PASSWORD_ERROR_MESSAGE, FIRSTNAME_REQUIRED, LASTNAME_REQUIRED,
  FIRSTNAME_TOO_LONG, LASTNAME_TOO_LONG,
} from '../../../../constants/Strings';
import SignUpDefault from '../Default';

describe('Create test case for Default Sign up component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <SignUpDefault switchToSignInForm={() => null} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Create a test case where form is not submitted because first name was too long', () => {
    render(
      <Provider store={store}>
        <SignUpDefault switchToSignInForm={() => null} />
      </Provider>,
    );
    const firstNameInputElement = screen.queryByPlaceholderText('Fill in first name') as HTMLInputElement;
    const lastNameInputElement = screen.queryByPlaceholderText('Fill in last name') as HTMLInputElement;
    const emailInputElement = screen.queryByPlaceholderText('ex: namesurname@gmail.com') as HTMLInputElement;
    const passwordInputElement = screen.queryByPlaceholderText('Enter your password') as HTMLInputElement;
    const submitButtonElement = screen.queryByTestId('signUpButton') as HTMLButtonElement;

    userEvent.type(firstNameInputElement, 'jwfjklwjfjsjfwoejfwofowfowjfojjfonvnvnjdfnvkdbjvbdjfvkjndknvkdfnvndfvn');
    userEvent.type(lastNameInputElement, 'Van Damn');
    userEvent.type(emailInputElement, 'blabala@blabla.com');
    userEvent.type(passwordInputElement, 'P@ssword11');
    userEvent.click(submitButtonElement);

    const firstNameErrorField = screen.queryByTestId('firstnameErrorField');
    const lastNameErrorField = screen.queryByTestId('lastnameErrorField');

    expect(firstNameErrorField).toBeInTheDocument();
    expect(lastNameErrorField).not.toBeInTheDocument();
    expect(firstNameErrorField).toHaveTextContent(FIRSTNAME_TOO_LONG);
  });
  test('Create a test case where form is not submitted because last name was too long', () => {
    render(
      <Provider store={store}>
        <SignUpDefault switchToSignInForm={() => null} />
      </Provider>,
    );
    const firstNameInputElement = screen.queryByPlaceholderText('Fill in first name') as HTMLInputElement;
    const lastNameInputElement = screen.queryByPlaceholderText('Fill in last name') as HTMLInputElement;
    const emailInputElement = screen.queryByPlaceholderText('ex: namesurname@gmail.com') as HTMLInputElement;
    const passwordInputElement = screen.queryByPlaceholderText('Enter your password') as HTMLInputElement;
    const submitButtonElement = screen.queryByTestId('signUpButton') as HTMLButtonElement;

    userEvent.type(firstNameInputElement, 'Jean Claude');
    userEvent.type(lastNameInputElement, 'jwfjklwjfjsjfwoejfwofowfowjfojjfonvnvnjdfnvkdbjvbdjfvkjndknvkdfnvndfvn');
    userEvent.type(emailInputElement, 'blabala@blabla.com');
    userEvent.type(passwordInputElement, 'P@ssword11');
    userEvent.click(submitButtonElement);

    const firstNameErrorField = screen.queryByTestId('firstnameErrorField');
    const lastNameErrorField = screen.queryByTestId('lastnameErrorField');

    expect(firstNameErrorField).not.toBeInTheDocument();
    expect(lastNameErrorField).toBeInTheDocument();
    expect(lastNameErrorField).toHaveTextContent(LASTNAME_TOO_LONG);
  });
  test('Create a test case where form is not submitted because email was too long', () => {
    render(
      <Provider store={store}>
        <SignUpDefault switchToSignInForm={() => null} />
      </Provider>,
    );
    const firstNameInputElement = screen.queryByPlaceholderText('Fill in first name') as HTMLInputElement;
    const lastNameInputElement = screen.queryByPlaceholderText('Fill in last name') as HTMLInputElement;
    const emailInputElement = screen.queryByPlaceholderText('ex: namesurname@gmail.com') as HTMLInputElement;
    const passwordInputElement = screen.queryByPlaceholderText('Enter your password') as HTMLInputElement;
    const submitButtonElement = screen.queryByTestId('signUpButton') as HTMLButtonElement;

    userEvent.type(firstNameInputElement, 'Jean Claude');
    userEvent.type(lastNameInputElement, 'Van Damn');
    userEvent.type(emailInputElement, 'jwfjklwjfjsjfwoejfwofowfowjfojjfonvnvnjdfnvkdbjvbdjfvkjndknvkdfnvndfvn@blabla.com');
    userEvent.type(passwordInputElement, 'P@ssword11');
    userEvent.click(submitButtonElement);

    const firstNameErrorField = screen.queryByTestId('firstnameErrorField');
    const lastNameErrorField = screen.queryByTestId('lastnameErrorField');
    const emailErrorField = screen.queryByTestId('emailErrorField');

    expect(firstNameErrorField).not.toBeInTheDocument();
    expect(lastNameErrorField).not.toBeInTheDocument();
    expect(emailErrorField).toBeInTheDocument();
    expect(emailErrorField).toHaveTextContent(EMAIL_TOO_LONG);
  });
  test('Create a test case where form is not submitted because first name was not valid', () => {
    render(
      <Provider store={store}>
        <SignUpDefault switchToSignInForm={() => null} />
      </Provider>,
    );
    const firstNameInputElement = screen.queryByPlaceholderText('Fill in first name') as HTMLInputElement;
    const lastNameInputElement = screen.queryByPlaceholderText('Fill in last name') as HTMLInputElement;
    const emailInputElement = screen.queryByPlaceholderText('ex: namesurname@gmail.com') as HTMLInputElement;
    const passwordInputElement = screen.queryByPlaceholderText('Enter your password') as HTMLInputElement;
    const submitButtonElement = screen.queryByTestId('signUpButton') as HTMLButtonElement;

    userEvent.type(firstNameInputElement, '');
    userEvent.type(lastNameInputElement, 'Van Damn');
    userEvent.type(emailInputElement, 'blabala@blabla.com');
    userEvent.type(passwordInputElement, 'P@ssword11');
    userEvent.click(submitButtonElement);

    const firstNameErrorField = screen.queryByTestId('firstnameErrorField');
    const lastNameErrorField = screen.queryByTestId('lastnameErrorField');

    expect(firstNameErrorField).toBeInTheDocument();
    expect(lastNameErrorField).not.toBeInTheDocument();
    expect(firstNameErrorField).toHaveTextContent(FIRSTNAME_REQUIRED);
  });
  test('Create a test case where form is not submitted because last name was not valid', () => {
    render(
      <Provider store={store}>
        <SignUpDefault switchToSignInForm={() => null} />
      </Provider>,
    );
    const firstNameInputElement = screen.queryByPlaceholderText('Fill in first name') as HTMLInputElement;
    const lastNameInputElement = screen.queryByPlaceholderText('Fill in last name') as HTMLInputElement;
    const emailInputElement = screen.queryByPlaceholderText('ex: namesurname@gmail.com') as HTMLInputElement;
    const passwordInputElement = screen.queryByPlaceholderText('Enter your password') as HTMLInputElement;
    const submitButtonElement = screen.queryByTestId('signUpButton') as HTMLButtonElement;

    userEvent.type(firstNameInputElement, 'Jean Claude');
    userEvent.type(lastNameInputElement, '');
    userEvent.type(emailInputElement, 'blabala@blabla.com');
    userEvent.type(passwordInputElement, 'P@ssword11');
    userEvent.click(submitButtonElement);

    const firstNameErrorField = screen.queryByTestId('firstnameErrorField');
    const lastNameErrorField = screen.queryByTestId('lastnameErrorField');

    expect(firstNameErrorField).not.toBeInTheDocument();
    expect(lastNameErrorField).toBeInTheDocument();
    expect(lastNameErrorField).toHaveTextContent(LASTNAME_REQUIRED);
  });
  test('Create a test case where form is not submitted because email was not inserted', () => {
    render(
      <Provider store={store}>
        <SignUpDefault switchToSignInForm={() => null} />
      </Provider>,
    );
    const firstNameInputElement = screen.queryByPlaceholderText('Fill in first name') as HTMLInputElement;
    const lastNameInputElement = screen.queryByPlaceholderText('Fill in last name') as HTMLInputElement;
    const emailInputElement = screen.queryByPlaceholderText('ex: namesurname@gmail.com') as HTMLInputElement;
    const passwordInputElement = screen.queryByPlaceholderText('Enter your password') as HTMLInputElement;
    const submitButtonElement = screen.queryByTestId('signUpButton') as HTMLButtonElement;

    userEvent.type(firstNameInputElement, 'Jean Claude');
    userEvent.type(lastNameInputElement, 'Van Damn');
    userEvent.type(emailInputElement, '');
    userEvent.type(passwordInputElement, 'P@ssword11');
    userEvent.click(submitButtonElement);

    const firstNameErrorField = screen.queryByTestId('firstnameErrorField');
    const lastNameErrorField = screen.queryByTestId('lastnameErrorField');
    const emailErrorField = screen.queryByTestId('emailErrorField');

    expect(firstNameErrorField).not.toBeInTheDocument();
    expect(lastNameErrorField).not.toBeInTheDocument();
    expect(emailErrorField).toBeInTheDocument();
    expect(emailErrorField).toHaveTextContent(EMAIL_REQUIRED);
  });
  test('Create a test case where form is not submitted because password was not inserted', () => {
    render(
      <Provider store={store}>
        <SignUpDefault switchToSignInForm={() => null} />
      </Provider>,
    );
    const firstNameInputElement = screen.queryByPlaceholderText('Fill in first name') as HTMLInputElement;
    const lastNameInputElement = screen.queryByPlaceholderText('Fill in last name') as HTMLInputElement;
    const emailInputElement = screen.queryByPlaceholderText('ex: namesurname@gmail.com') as HTMLInputElement;
    const passwordInputElement = screen.queryByPlaceholderText('Enter your password') as HTMLInputElement;
    const submitButtonElement = screen.queryByTestId('signUpButton') as HTMLButtonElement;

    userEvent.type(firstNameInputElement, 'Jean Claude');
    userEvent.type(lastNameInputElement, 'Van Damn');
    userEvent.type(emailInputElement, 'blabala@blabla.com');
    userEvent.type(passwordInputElement, '');
    userEvent.click(submitButtonElement);

    const firstNameErrorField = screen.queryByTestId('firstnameErrorField');
    const lastNameErrorField = screen.queryByTestId('lastnameErrorField');
    const emailErrorField = screen.queryByTestId('emailErrorField');
    const passwordErrorField = screen.queryByTestId('psdErrorField');

    expect(firstNameErrorField).not.toBeInTheDocument();
    expect(lastNameErrorField).not.toBeInTheDocument();
    expect(emailErrorField).not.toBeInTheDocument();
    expect(passwordErrorField).toBeInTheDocument();
    expect(passwordErrorField).toHaveTextContent(PASSWORD_REQUIRED);
  });
  test('Create a test case where form is not submitted because email was not valid', () => {
    render(
      <Provider store={store}>
        <SignUpDefault switchToSignInForm={() => null} />
      </Provider>,
    );
    const firstNameInputElement = screen.queryByPlaceholderText('Fill in first name') as HTMLInputElement;
    const lastNameInputElement = screen.queryByPlaceholderText('Fill in last name') as HTMLInputElement;
    const emailInputElement = screen.queryByPlaceholderText('ex: namesurname@gmail.com') as HTMLInputElement;
    const passwordInputElement = screen.queryByPlaceholderText('Enter your password') as HTMLInputElement;
    const submitButtonElement = screen.queryByTestId('signUpButton') as HTMLButtonElement;

    userEvent.type(firstNameInputElement, 'Jean Claude');
    userEvent.type(lastNameInputElement, 'Van Damn');
    userEvent.type(emailInputElement, 'blabla');
    userEvent.type(passwordInputElement, 'P@ssword11');
    userEvent.click(submitButtonElement);

    const firstNameErrorField = screen.queryByTestId('firstnameErrorField');
    const lastNameErrorField = screen.queryByTestId('lastnameErrorField');
    const emailErrorField = screen.queryByTestId('emailErrorField');

    expect(firstNameErrorField).not.toBeInTheDocument();
    expect(lastNameErrorField).not.toBeInTheDocument();
    expect(emailErrorField).toBeInTheDocument();
    expect(emailErrorField).toHaveTextContent(INVALID_EMAIL_ERROR_MESSAGE);
  });
  test('Create a test case where form is not submitted because password was not valid', () => {
    render(
      <Provider store={store}>
        <SignUpDefault switchToSignInForm={() => null} />
      </Provider>,
    );
    const firstNameInputElement = screen.queryByPlaceholderText('Fill in first name') as HTMLInputElement;
    const lastNameInputElement = screen.queryByPlaceholderText('Fill in last name') as HTMLInputElement;
    const emailInputElement = screen.queryByPlaceholderText('ex: namesurname@gmail.com') as HTMLInputElement;
    const passwordInputElement = screen.queryByPlaceholderText('Enter your password') as HTMLInputElement;
    const submitButtonElement = screen.queryByTestId('signUpButton') as HTMLButtonElement;

    userEvent.type(firstNameInputElement, 'Jean Claude');
    userEvent.type(lastNameInputElement, 'Van Damn');
    userEvent.type(emailInputElement, 'blabala@blabla.com');
    userEvent.type(passwordInputElement, 'password');
    userEvent.click(submitButtonElement);

    const firstNameErrorField = screen.queryByTestId('firstnameErrorField');
    const lastNameErrorField = screen.queryByTestId('lastnameErrorField');
    const emailErrorField = screen.queryByTestId('emailErrorField');
    const passwordErrorField = screen.queryByTestId('psdErrorField');

    expect(firstNameErrorField).not.toBeInTheDocument();
    expect(lastNameErrorField).not.toBeInTheDocument();
    expect(emailErrorField).not.toBeInTheDocument();
    expect(passwordErrorField).toBeInTheDocument();
    expect(passwordErrorField).toHaveTextContent(INVALID_PASSWORD_ERROR_MESSAGE);
  });
});
