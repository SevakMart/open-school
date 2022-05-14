import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import userEvent from '@testing-library/user-event';
import { Provider } from 'react-redux';
import { store } from '../../../../redux/Store';
import { EMAIL_REQUIRED, INVALID_EMAIL_ERROR_MESSAGE } from '../../../../constants/Strings';
/* import * as forgotPasswordRequest from '../../../../services/sendForgotPasswordRequest';
import ForgotPassword from '../ForgotPassword';

const successData = {
  data: {
    message: 'Email was send successfully',
  },
  status: 200,
};
const failedData = {
  data: {
    message: 'This email does not exist in the database',
  },
  status: 400,
};

describe('Create test cases for ForgotPassword component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <ForgotPassword returnToSignInForm={() => null} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Create a test where we click continue without adding an email on input field', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <ForgotPassword returnToSignInForm={() => null} />
      </Provider>,
    );
    const continueButton = screen.queryByTestId('continueButton');

    userEvent.click((continueButton as HTMLButtonElement));

    const emailErrorForgotPasswordElement = screen.queryByTestId('emailErrorForgotPassword');

    expect(emailErrorForgotPasswordElement).toBeInTheDocument();
    expect(emailErrorForgotPasswordElement).toHaveTextContent(EMAIL_REQUIRED);
  });
  test('Input an invalid email address', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <ForgotPassword returnToSignInForm={() => null} />
      </Provider>,
    );
    const forgotPasswordEmailInputElement = screen.queryByTestId('forgotPasswordEmailInput');
    const continueButton = screen.queryByTestId('continueButton');

    userEvent.type((forgotPasswordEmailInputElement as HTMLInputElement), 'john');
    userEvent.click((continueButton as HTMLButtonElement));

    const emailErrorForgotPasswordElement = screen.queryByTestId('emailErrorForgotPassword');

    expect(emailErrorForgotPasswordElement).toBeInTheDocument();
    expect(emailErrorForgotPasswordElement).toHaveTextContent(INVALID_EMAIL_ERROR_MESSAGE);
  });
  test('Create a successful mail sending', async () => {
    expect.hasAssertions();
    jest.spyOn(forgotPasswordRequest, 'sendForgotPasswordRequest').mockResolvedValue(successData);

    render(
      <Provider store={store}>
        <ForgotPassword returnToSignInForm={() => null} />
      </Provider>,
    );
    const forgotPasswordEmailInputElement = screen.queryByTestId('forgotPasswordEmailInput');
    const continueButton = screen.queryByTestId('continueButton');

    userEvent.type((forgotPasswordEmailInputElement as HTMLInputElement), 'john@john.com');
    userEvent.click((continueButton as HTMLButtonElement));

    const forgotPasswordSuccessMessageElement = await screen.findByTestId('forgotPasswordSuccessMessage');

    expect(forgotPasswordSuccessMessageElement).toBeInTheDocument();
    expect(forgotPasswordSuccessMessageElement).toHaveTextContent(successData.data.message);
  });

  test('Create a successful mail sending', async () => {
    expect.hasAssertions();
    jest.spyOn(forgotPasswordRequest, 'sendForgotPasswordRequest').mockResolvedValue(failedData);

    render(
      <Provider store={store}>
        <ForgotPassword returnToSignInForm={() => null} />
      </Provider>,
    );
    const forgotPasswordEmailInputElement = screen.queryByTestId('forgotPasswordEmailInput');
    const continueButton = screen.queryByTestId('continueButton');

    userEvent.type((forgotPasswordEmailInputElement as HTMLInputElement), 'john@john.com');
    userEvent.click((continueButton as HTMLButtonElement));

    const emailErrorForgotPasswordElement = await screen.findByTestId('emailErrorForgotPassword');

    expect(emailErrorForgotPasswordElement).toBeInTheDocument();
    expect(emailErrorForgotPasswordElement).toHaveTextContent(failedData.data.message);
  });
});
*/
