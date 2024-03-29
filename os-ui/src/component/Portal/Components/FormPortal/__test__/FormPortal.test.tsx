import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router-dom';
import { store } from '../../../../../redux/Store';
import { FormPortal } from '../FormPortal';

const mockedUseNavigate = jest.fn();

jest.mock('react-router-dom', () => ({
  ...(jest.requireActual('react-router-dom') as any),
  useNavigate: () => mockedUseNavigate,
}));

describe('Create unit tests for FormPortal component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <MemoryRouter>
          <FormPortal isOpen children={<h2>Hello world</h2>} />
        </MemoryRouter>
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
