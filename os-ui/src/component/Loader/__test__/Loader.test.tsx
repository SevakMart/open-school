import { render } from '@testing-library/react';
import Loader from '../Loader';

test('Create a Snapshot testing', () => {
  const { asFragment } = render(<Loader />);
  expect(asFragment()).toMatchSnapshot();
});
