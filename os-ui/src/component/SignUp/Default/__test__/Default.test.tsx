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

const inputCases = [
  ['jwfjklwjfjsjfwoejfwofowfowjfojjfonvnvnjdfnvkdbjvbdjfvkjndknvkdfnvndfvn', 'Van Damn', 'blabala@blabla.com', 'P@ssword11'],
  ['Jean Claude', 'jwfjklwjfjsjfwoejfwofowfowjfojjfonvnvnjdfnvkdbjvbdjfvkjndknvkdfnvndfvn', 'blabala@blabla.com', 'P@ssword11'],
  ['Jean Claude', 'Van Damn', 'jwfjklwjfjsjfwoejfwofowfowjfojjfonvnvnjdfnvkdbjvbdjfvkjndknvkdfnvndfvn', 'P@ssword11'],
  ['', 'Van Damn', 'blabala@blabla.com', 'P@ssword11'],
  ['Jean Claude', '', 'blabala@blabla.com', 'P@ssword11'],
  ['Jean Claude', 'Van Damn', '', 'P@ssword11'],
  ['Jean Claude', 'Van Damn', 'blabala@blabla.com', ''],
  ['Jean Claude', 'Van Damn', 'blabala', 'P@ssword11'],
  ['Jean Claude', 'Van Damn', 'blabala@blabla.com', 'password'],
];

describe('Create test case for Default Sign up component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <SignUpDefault switchToSignInForm={() => null} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test.each(inputCases)('Create test cases for input element', (firstName, lastName, email, password) => {
    render(
      <Provider store={store}>
        <SignUpDefault switchToSignInForm={() => null} />
      </Provider>,
    );
    const firstNameInputElement = screen.queryByTestId('firstNameInput') as HTMLInputElement;
    const lastNameInputElement = screen.queryByTestId('lastNameInput') as HTMLInputElement;
    const emailInputElement = screen.queryByTestId('emailInput') as HTMLInputElement;
    const passwordInputElement = screen.queryByTestId('psdInput') as HTMLInputElement;
    const submitButtonElement = screen.queryByTestId('signUpButton') as HTMLButtonElement;

    userEvent.type(firstNameInputElement, firstName);
    userEvent.type(lastNameInputElement, lastName);
    userEvent.type(emailInputElement, email);
    userEvent.type(passwordInputElement, password);
    userEvent.click(submitButtonElement);

    const firstNameErrorField = screen.queryByTestId('firstnameErrorField');
    const lastNameErrorField = screen.queryByTestId('lastnameErrorField');
    const emailErrorField = screen.queryByTestId('emailErrorField');
    const passwordErrorField = screen.queryByTestId('psdErrorField');

    firstNameErrorField && expect(firstNameErrorField).toBeInTheDocument();
    /* eslint-disable-next-line max-len */
    firstNameErrorField && expect(firstNameErrorField).toHaveTextContent((firstName.length > 45 ? FIRSTNAME_TOO_LONG : FIRSTNAME_REQUIRED));
    lastNameErrorField && expect(lastNameErrorField).toBeInTheDocument();
    /* eslint-disable-next-line max-len */
    lastNameErrorField && expect(lastNameErrorField).toHaveTextContent(lastName.length > 45 ? LASTNAME_TOO_LONG : LASTNAME_REQUIRED);
    emailErrorField && expect(emailErrorField).toBeInTheDocument();
    /* eslint-disable-next-line max-len */
    emailErrorField && expect(emailErrorField).toHaveTextContent(email.length > 45 ? EMAIL_TOO_LONG : email.length < 1 ? EMAIL_REQUIRED : INVALID_EMAIL_ERROR_MESSAGE);
    passwordErrorField && expect(passwordErrorField).toBeInTheDocument();
    /* eslint-disable-next-line max-len */
    passwordErrorField && expect(passwordErrorField).toHaveTextContent(password.length < 1 ? PASSWORD_REQUIRED : INVALID_PASSWORD_ERROR_MESSAGE);
  });
});
