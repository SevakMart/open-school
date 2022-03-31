import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import userEvent from '@testing-library/user-event';
import SignIn from '../SignIn';

describe('Create tests for sign up form', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(<SignIn handleSignInClicks={() => null} />);
    expect(asFragment()).toMatchSnapshot();
  });
  test('Test if the form disappears after clicking the close button', () => {
    render(<SignIn handleSignInClicks={() => null} />);
    const closeButton = screen.queryByTestId('closeButton');
    const formComponent = screen.queryByRole('form');
    userEvent.click(closeButton as HTMLDivElement);
    expect(formComponent).not.toBeInTheDocument();
  });
});
