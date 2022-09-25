import { render } from '@testing-library/react';
import Footer from '../Footer';

describe('Create test cases for Footer component', () => {
  test('It should match the snapshot', () => {
    const { asFragment } = render(<Footer />);
    expect(asFragment()).toMatchSnapshot();
  });
});
