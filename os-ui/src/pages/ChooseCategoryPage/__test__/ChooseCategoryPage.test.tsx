import { render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import ChooseCategoryPage from '../ChooseCategoryPage';
import { store } from '../../../redux/Store';
import * as fetchSubcategories from '../../../services/getSearchedCategories';

const mockUseNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
}));
const data = {
  Java: [
    {
      id: 1,
      title: 'Arrays',
    },
    {
      id: 2,
      title: 'Functions',
    },
    {
      id: 3,
      title: 'Classes',
    },

  ],
};

describe('Create test case to ChooSecategoryPage', () => {
  test('Create a snapshot test', async () => {
    const { asFragment } = render(
      <Provider store={store}>
        <ChooseCategoryPage />
      </Provider>,
    );
    expect(asFragment).toMatchSnapshot();
  });
  test('Check the content of the component', async () => {
    jest.spyOn(fetchSubcategories, 'getSearchedCategories').mockResolvedValue(data);
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <ChooseCategoryPage />
      </Provider>,
    );
    const headerElement = await screen.findByTestId('parentCategoryHeader');
    const labelElement = await screen.findByTestId('Functions');
    expect(headerElement).toBeInTheDocument();
    expect(headerElement).toHaveTextContent('Java');
    expect(labelElement).toBeInTheDocument();
    expect(labelElement).toHaveTextContent('Functions');
  });
});
