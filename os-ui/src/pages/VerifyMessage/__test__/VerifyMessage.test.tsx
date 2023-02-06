import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import VerifyMessage from '../VerifyMessage';

test('Create a snapshot test', () => {
  const { asFragment } = render(
    <Provider store={store}>
      <VerifyMessage />
    </Provider>,
  );
  expect(asFragment()).toMatchSnapshot();
});
