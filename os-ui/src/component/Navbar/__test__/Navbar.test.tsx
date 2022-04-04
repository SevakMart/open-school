import { render } from '@testing-library/react';
import Navbar from '../Navbar';

describe('Make unit tests of Navbar component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(<Navbar />);
    expect(asFragment()).toMatchSnapshot();
  });
});
