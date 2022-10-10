import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import Header from '../Header';

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

describe('Create test cases for Header', () => {
  test('Create snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <Header
          userId={1}
          token="123"
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
