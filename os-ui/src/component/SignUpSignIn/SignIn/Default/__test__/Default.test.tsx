import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import userEvent from '@testing-library/user-event';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import { PASSWORD_REQUIRED, EMAIL_REQUIRED, INVALID_EMAIL_ERROR_MESSAGE } from '../../../../../constants/Strings';
import SignInDefault from '../Default';

describe('Create Test files for default signIn component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <SignInDefault
          handleSignIn={() => null}
          forgotPasswordFunc={() => null}
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Create a test case where email is not inserted', () => {
    render(
      <Provider store={store}>
        <SignInDefault
          handleSignIn={() => null}
          forgotPasswordFunc={() => null}
        />
      </Provider>,
    );
    const emailInputElement = screen.queryByTestId('emailInput') as HTMLInputElement;
    const passwordInputElement = screen.queryByTestId('psdInput') as HTMLInputElement;
    const signInButton = screen.queryByTestId('signInButton') as HTMLButtonElement;

    userEvent.type(emailInputElement, '');
    userEvent.type(passwordInputElement, 'P@ssword11');
    userEvent.click(signInButton);

    const emailErrorField = screen.queryByTestId('emailErrorField');
    const passwordErrorField = screen.queryByTestId('passwordErrorField');

    expect(emailErrorField).toBeInTheDocument();
    expect(passwordErrorField).not.toBeInTheDocument();
    expect(emailErrorField).toHaveTextContent(EMAIL_REQUIRED);
  });
  test('Create a test case where password is not inserted', () => {
    render(
      <Provider store={store}>
        <SignInDefault
          handleSignIn={() => null}
          forgotPasswordFunc={() => null}
        />
      </Provider>,
    );
    const emailInputElement = screen.queryByTestId('emailInput') as HTMLInputElement;
    const passwordInputElement = screen.queryByTestId('psdInput') as HTMLInputElement;
    const signInButton = screen.queryByTestId('signInButton') as HTMLButtonElement;

    userEvent.type(emailInputElement, 'blabla@blabla.com');
    userEvent.type(passwordInputElement, '');
    userEvent.click(signInButton);

    const emailErrorField = screen.queryByTestId('emailErrorField');
    const passwordErrorField = screen.queryByTestId('passwordErrorField');

    expect(emailErrorField).not.toBeInTheDocument();
    expect(passwordErrorField).toBeInTheDocument();
    expect(passwordErrorField).toHaveTextContent(PASSWORD_REQUIRED);
  });
  test('Create a test case where email is not valid', () => {
    render(
      <Provider store={store}>
        <SignInDefault
          handleSignIn={() => null}
          forgotPasswordFunc={() => null}
        />
      </Provider>,
    );
    const emailInputElement = screen.queryByTestId('emailInput') as HTMLInputElement;
    const passwordInputElement = screen.queryByTestId('psdInput') as HTMLInputElement;
    const signInButton = screen.queryByTestId('signInButton') as HTMLButtonElement;

    userEvent.type(emailInputElement, 'blabla');
    userEvent.type(passwordInputElement, 'P@ssword11');
    userEvent.click(signInButton);

    const emailErrorField = screen.queryByTestId('emailErrorField');
    const passwordErrorField = screen.queryByTestId('passwordErrorField');

    expect(emailErrorField).toBeInTheDocument();
    expect(passwordErrorField).not.toBeInTheDocument();
    expect(emailErrorField).toHaveTextContent(INVALID_EMAIL_ERROR_MESSAGE);
  });
});
