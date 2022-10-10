import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../../redux/Store';
import NavbarOnSignIn from '../NavbarOnSignIn';

const mockUseNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
  useLocation: () => ({
    search: '',
  }),
}));

describe('Make unit tests of NavbarOnSignIn component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(<Provider store={store}><NavbarOnSignIn /></Provider>);
    expect(asFragment()).toMatchSnapshot();
  });
});
