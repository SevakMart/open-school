import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import FilteringContent from '../FilteringContent';

const mockUseNavigate = jest.fn();
const mockUseLocation = jest.fn();

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
  useLocation: () => mockUseLocation,
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
});
