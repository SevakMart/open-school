import { render } from '@testing-library/react';
import NoCourses from '../NoCourses';

test('Create a snapshot test', () => {
  const { asFragment } = render(<NoCourses />);
  expect(asFragment()).toMatchSnapshot();
});
