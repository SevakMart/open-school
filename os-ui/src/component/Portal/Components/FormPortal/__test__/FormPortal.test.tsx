import { render } from '@testing-library/react';
import { FormPortal } from '../FormPortal';

describe('Create unit tests for FormPortal component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(<FormPortal isOpen children={<h2>Hello world</h2>} />);
    expect(asFragment()).toMatchSnapshot();
  });
});
