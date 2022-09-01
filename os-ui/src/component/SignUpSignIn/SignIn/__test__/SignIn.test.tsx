import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../redux/Store';
import SignIn from '../SignIn';

/* const mockNavigate = jest.fn();

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: () => mockNavigate,
}));

describe('Create test cases for SignIn component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <SignIn
          handleSignInClicks={() => null}
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
*/
