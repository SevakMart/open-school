import { render } from '@testing-library/react';
import NavbarOnSignIn from '../NavbarOnSignIn';

describe('Make unit tests of NavbarOnSignIn component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(<NavbarOnSignIn />);
    expect(asFragment()).toMatchSnapshot();
  });
});
