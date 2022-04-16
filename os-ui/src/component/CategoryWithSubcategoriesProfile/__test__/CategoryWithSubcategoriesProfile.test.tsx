import { render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import CategoryWithSubcategoriesProfile from '../CategoryWithSubcategoriesProfile';
import { store } from '../../../redux/Store';

const subcategoriesArray = [
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

];

describe('Create tests for the Component', () => {
  test('Create a Snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <CategoryWithSubcategoriesProfile
          parentCategory="Java"
          subcategories={subcategoriesArray}
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Check the content of the component', () => {
    render(
      <Provider store={store}>
        <CategoryWithSubcategoriesProfile
          parentCategory="Java"
          subcategories={subcategoriesArray}
        />
      </Provider>,
    );
    const headerElement = screen.queryByTestId('Java');
    const labelElement = screen.queryByTestId('Functions');
    expect(headerElement).toBeInTheDocument();
    expect(headerElement).toHaveTextContent('Java');
    expect(labelElement).toBeInTheDocument();
    expect(labelElement).toHaveTextContent('Functions');
  });
});
