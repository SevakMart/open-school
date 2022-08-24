import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
import { store } from '../../../../../redux/Store';
import FilteringContent from '../FilteringContent';

const mockUseNavigate = jest.fn();
const mockUseLocation = jest.fn();

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
  useLocation: () => mockUseLocation,
}));
jest.mock('redux-state-sync', () => ({
  createStateSyncMiddleware:
    () => () => (next: (action: PayloadAction) => void) => (action: PayloadAction) => next(action),
  initMessageListener: () => jest.fn(),
}));

const contentAsString = {
  1: 'French',
  2: 'English',
  3: 'Arabic',
};
const contentAsObject = {
  JS: {
    1: 'React',
    2: 'Angular',
    3: 'Vue Js',
  },
  Android: {
    1: 'Java',
    2: 'Kotlin',
  },
};

describe('Create test cases for filteringcontent component where the content is of type string', () => {
  test('Create a snapshot case', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <FilteringContent
          title="Languages"
          content={contentAsString}
          filterFeature="languageIds"
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Test if the component renders as expected', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <FilteringContent
          title="Languages"
          content={contentAsString}
          filterFeature="languageIds"
        />
      </Provider>,
    );
    const mainFilteringTitleElement = screen.queryByTestId('Languages');
    const labelOfCheckedElement = screen.queryByTestId('label of French');

    expect(mainFilteringTitleElement).toBeInTheDocument();
    expect(mainFilteringTitleElement).toHaveTextContent('Languages');
    expect(labelOfCheckedElement).toBeInTheDocument();
    expect(labelOfCheckedElement).toHaveTextContent('French');
  });
});
describe('Create test cases where the content element is of type object', () => {
  test('Create a snapshot case', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <FilteringContent
          title="Category"
          content={contentAsObject}
          filterFeature="subCategoryIds"
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Test if the component renders as expected', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <FilteringContent
          title="Category"
          content={contentAsObject}
          filterFeature="subCategoryIds"
        />
      </Provider>,
    );
    const mainFilteringTitleElement = screen.queryByTestId('Category');
    const filterFeatureElement = screen.queryByTestId('JS');
    const labelOfCheckedElement = screen.queryByTestId('label of React');

    expect(mainFilteringTitleElement).toBeInTheDocument();
    expect(mainFilteringTitleElement).toHaveTextContent('Category');
    expect(filterFeatureElement).toBeInTheDocument();
    expect(filterFeatureElement).toHaveTextContent('JS');
    expect(labelOfCheckedElement).toBeInTheDocument();
    expect(labelOfCheckedElement).toHaveTextContent('React');
  });
});
