import { render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import ChooseCategoryPage from '../ChooseCategoryPage';
import { store } from '../../../redux/Store';
import categoriesService from '../../../services/categoriesService';

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
  Python: [
    {
      id: 4,
      title: 'Python Arrays',
    },
    {
      id: 5,
      title: 'Python Functions',
    },
    {
      id: 6,
      title: 'Python Classes',
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
    jest.spyOn(categoriesService, 'getSearchedCategories').mockResolvedValue(data);
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <ChooseCategoryPage />
      </Provider>,
    );
    const headerElement1 = await screen.findByTestId('Java');
    const headerElement2 = await screen.findByTestId('Python');
    const labelElement1 = await screen.findByTestId('Functions');
    const labelElement2 = await screen.findByTestId('Python Functions');

    expect(headerElement1).toBeInTheDocument();
    expect(headerElement1).toHaveTextContent('Java');
    expect(labelElement1).toBeInTheDocument();
    expect(labelElement1).toHaveTextContent('Functions');
    expect(headerElement2).toBeInTheDocument();
    expect(headerElement2).toHaveTextContent('Python');
    expect(labelElement2).toBeInTheDocument();
    expect(labelElement2).toHaveTextContent('Python Functions');
  });
  test('Test the output of page when the searched data is not found', async () => {
    jest.spyOn(categoriesService, 'getSearchedCategories').mockResolvedValue({});
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <ChooseCategoryPage />
      </Provider>,
    );
    const errorMessageHeader = await screen.findByTestId('chooseSubcategoriesErrorMessage');
    expect(errorMessageHeader).toBeInTheDocument();
    expect(errorMessageHeader).toHaveTextContent('Something went wrong please refresh the page');
  });
});
