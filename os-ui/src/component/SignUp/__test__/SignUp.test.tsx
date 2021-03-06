import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import userEvent from '@testing-library/user-event';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import authService from '../../../services/authService';
import SignUp from '../SignUp';

describe('Create tests for sign up form', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <SignUp handleSignUpClicks={() => null} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Test if the form disappears after clicking the close button', () => {
    render(
      <Provider store={store}>
        <SignUp handleSignUpClicks={() => null} />
      </Provider>,
    );
    const closeButton = screen.queryByTestId('closeButton');
    const formComponent = screen.queryByRole('form');
    userEvent.click(closeButton as HTMLDivElement);
    expect(formComponent).not.toBeInTheDocument();
  });
  test('Test if successful sign up message appear after clicking sign up button', async () => {
    expect.hasAssertions();
    jest.spyOn(authService, 'register').mockResolvedValue({ status: 201 });
    render(
      <Provider store={store}>
        <SignUp handleSignUpClicks={() => null} />
      </Provider>,
    );
    const firstNameInputField = screen.queryByTestId('firstNameInput');
    const lastNameInputField = screen.queryByTestId('lastNameInput');
    const emailInputField = screen.queryByTestId('emailInput');
    const passwordInputField = screen.queryByTestId('psdInput');
    const signUpButton = screen.queryByTestId('signUpButton');

    userEvent.type(firstNameInputField as HTMLInputElement, 'hello');
    userEvent.type(lastNameInputField as HTMLInputElement, 'world');
    userEvent.type(emailInputField as HTMLInputElement, 'hello@hello.com');
    userEvent.type(passwordInputField as HTMLInputElement, 'HelloWorld93$');
    userEvent.click(signUpButton as HTMLButtonElement);

    const successfulMessageHeader = await screen.findByTestId('successfulSignUpMessage');

    expect(successfulMessageHeader).toBeInTheDocument();
    expect(firstNameInputField).not.toBeInTheDocument();
    expect(lastNameInputField).not.toBeInTheDocument();
    expect(emailInputField).not.toBeInTheDocument();
    expect(passwordInputField).not.toBeInTheDocument();
    expect(signUpButton).not.toBeInTheDocument();
  });
});
