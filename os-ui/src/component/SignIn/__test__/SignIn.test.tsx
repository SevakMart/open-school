import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import userEvent from '@testing-library/user-event';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import SignIn from '../SignIn';
/* import * as signInForm from '../../../services/signIn';

const loggedInUserData = {
  name: 'John Smith',
  roleType: 'STUDENT',
  status: 200,
  token: '123456',
};
const failedLoginUser = {
  message: 'Invalid email or password',
  status: 401,
};
const mockedUseNavigate = jest.fn();

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockedUseNavigate,
}));
describe('Create tests for sign up form', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <SignIn handleSignInClicks={() => null} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Test if the form disappears after clicking the close button', () => {
    render(
      <Provider store={store}>
        <SignIn handleSignInClicks={() => null} />
      </Provider>,
    );
    const closeButton = screen.queryByTestId('closeButton');
    const formComponent = screen.queryByRole('form');
    userEvent.click(closeButton as HTMLDivElement);
    expect(formComponent).not.toBeInTheDocument();
  });
  test('Test if successful signIn message appear after clicking sign in button', async () => {
    expect.hasAssertions();
    jest.spyOn(signInForm, 'signIn').mockResolvedValue(loggedInUserData);
    render(
      <Provider store={store}>
        <SignIn handleSignInClicks={() => null} />
      </Provider>,
    );
    const emailInputField = screen.queryByPlaceholderText('ex: namesurname@gmail.com');
    const passwordInputField = screen.queryByPlaceholderText('Enter your password');
    const signInButton = screen.queryByTestId('signInButton');

    userEvent.type(emailInputField as HTMLInputElement, 'hello@hello.com');
    userEvent.type(passwordInputField as HTMLInputElement, 'hello');
    userEvent.click(signInButton as HTMLButtonElement);

    const successfulSignInHeader = await screen.findByTestId('successfulSignInMessage');

    expect(successfulSignInHeader).toBeInTheDocument();
    expect(emailInputField).not.toBeInTheDocument();
    expect(passwordInputField).not.toBeInTheDocument();
  });
  test('Test a failed tentative of signing in', async () => {
    expect.hasAssertions();
    jest.spyOn(signInForm, 'signIn').mockResolvedValue(failedLoginUser);
    render(
      <Provider store={store}>
        <SignIn handleSignInClicks={() => null} />
      </Provider>,
    );
    const emailInputField = screen.queryByPlaceholderText('ex: namesurname@gmail.com');
    const passwordInputField = screen.queryByPlaceholderText('Enter your password');
    const signInButton = screen.queryByTestId('signInButton');

    userEvent.type(emailInputField as HTMLInputElement, 'hello@hello.com');
    userEvent.type(passwordInputField as HTMLInputElement, 'hello');
    userEvent.click(signInButton as HTMLButtonElement);

    const SigninErrorMessageHeader = await screen.findByTestId('signInErrorMessage');

    expect(SigninErrorMessageHeader).toBeInTheDocument();
    expect(SigninErrorMessageHeader).toHaveTextContent('Invalid email or password');
  });
}); */
