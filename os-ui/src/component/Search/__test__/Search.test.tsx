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
          changeUrlQueries={() => null}
          paddingLeft="6%"
          leftPosition="7%"
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Check Search input content', () => {
    render(
      <Provider store={store}>
        <Search
          changeUrlQueries={() => null}
          paddingLeft="6%"
          leftPosition="7%"
        />
      </Provider>,
    );
    const inputField = screen.getByPlaceholderText('Search name');
    userEvent.type(inputField, 'java');
    expect(inputField).toHaveValue('java');
  });
});
