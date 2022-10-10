import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import NotFoundPage from '../NotFoundPage';

test('Create a snapshot test', () => {
  const { asFragment } = render(
    <Provider store={store}>
      <NotFoundPage />
    </Provider>,
  );
  expect(asFragment()).toMatchSnapshot();
});
