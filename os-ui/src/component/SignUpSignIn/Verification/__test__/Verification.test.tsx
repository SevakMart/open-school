import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../redux/Store';
import Verification from '../Verification';

describe('Create test case for Verification component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <Verification />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
