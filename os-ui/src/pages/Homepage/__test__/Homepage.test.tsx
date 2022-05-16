import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import Homepage from '../Homepage';

describe('Create several unit tests for Homepage Component', () => {
  test('Create a Snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <Homepage />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
