import { render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
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

jest.mock('redux-state-sync', () => ({
  createStateSyncMiddleware:
    () => () => (next: (action: PayloadAction) => void) => (action: PayloadAction) => next(action),
  initMessageListener: () => jest.fn(),
}));

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
