import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Provider } from 'react-redux';
import Search from '../Search';
import { store } from '../../../redux/Store';

const mockUseNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
}));

describe('Create test case to Search component', () => {
  test('Create a Snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <Search
          pathname="/choose_categories"
          searchKeyName="searchCategories"
          changeUrlQueries={() => null}
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Check Search input content', () => {
    render(
      <Provider store={store}>
        <Search
          pathname="/choose_categories"
          searchKeyName="searchCategories"
          changeUrlQueries={() => null}
        />
      </Provider>,
    );
    const inputField = screen.getByPlaceholderText('Search name');
    userEvent.type(inputField, 'java');
    expect(inputField).toHaveValue('java');
  });
});
