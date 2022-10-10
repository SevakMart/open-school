import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../../../redux/Store';
import ModalMessageComponent from '../ModalMessageComponent';

const mockUseNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
  useLocation: () => ({
    search: '',
  }),
}));
jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));

describe('Create test cases for ModalMessageComponent', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <ModalMessageComponent />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
