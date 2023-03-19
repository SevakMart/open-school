import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
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
        <FormPortal isOpen children={<h2>Hello world</h2>} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
