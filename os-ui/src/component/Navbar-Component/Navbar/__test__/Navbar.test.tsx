import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../../redux/Store';
import Navbar from '../Navbar';

jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));
describe('Make unit tests of Navbar component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <Navbar />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
