import { render } from '@testing-library/react';
import { ProfilePortal } from '../ProfilePortal';

describe('Create unit tests for ProfilePortal component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(<ProfilePortal isOpen children={<h2>Hello world</h2>} />);
    expect(asFragment()).toMatchSnapshot();
  });
});
