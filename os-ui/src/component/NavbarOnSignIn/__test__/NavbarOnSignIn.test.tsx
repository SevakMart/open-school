import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import NavbarOnSignIn from '../NavbarOnSignIn';
import { store } from '../../../redux/Store';

const mockedUseNavigate = jest.fn();

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: () => mockedUseNavigate,
}));

test('Create a Snapshot test', () => {
  const { asFragment } = render(<Provider store={store}><NavbarOnSignIn /></Provider>);
  expect(asFragment()).toMatchSnapshot();
});
