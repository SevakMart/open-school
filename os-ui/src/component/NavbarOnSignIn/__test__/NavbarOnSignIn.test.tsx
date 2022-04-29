import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import NavbarOnSignIn from '../NavbarOnSignIn';
import { store } from '../../../redux/Store';

test('Create a Snapshot test', () => {
  const { asFragment } = render(<Provider store={store}><NavbarOnSignIn /></Provider>);
  expect(asFragment()).toMatchSnapshot();
});
