import { render } from '@testing-library/react';
import NoCourses from '../NoCourses';

jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));

test('Create a snapshot test', () => {
  const { asFragment } = render(<NoCourses />);
  expect(asFragment()).toMatchSnapshot();
});
