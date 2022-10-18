import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import ChooseCategoryPage from '../ChooseCategoryPage';
import { store } from '../../../redux/Store';

const mockUseNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
  useLocation: () => ({
    search: '',
  }),
}));
jest.mock('react-i18next', () => ({
  ...jest.requireActual('react-i18next'),
  useTranslation: () => ({
    t: (message:string) => {
      if (message === 'error') return 'Something went wrong please refresh the page';
    },
  }),
}));

const userInfo = {
  token: '123',
  id: 1,
};

describe('Create test case to ChooSecategoryPage', () => {
  test('Create a snapshot test', async () => {
    const { asFragment } = render(
      <Provider store={store}>
        <ChooseCategoryPage userInfo={userInfo} />
      </Provider>,
    );
    expect(asFragment).toMatchSnapshot();
  });
});
