import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import SubcategoryList from '../SubcategoryList';

const mockUseNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
}));
jest.mock('react-i18next', () => ({
  ...jest.requireActual('react-i18next'),
  useTranslation: () => ({
    t: (message:string) => {
      if (message === 'error') return 'Something went wrong please refresh the page';
    },
  }),
}));

describe('Create test case to SubcategoryList component', () => {
  test('Create a snapshot test', async () => {
    const { asFragment } = render(
      <Provider store={store}>
        <SubcategoryList
          subcategories={[{ id: 1, title: 'React' }]}
        />
      </Provider>,
    );
    expect(asFragment).toMatchSnapshot();
  });
});
