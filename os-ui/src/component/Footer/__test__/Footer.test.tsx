import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import Footer from '../Footer';

describe('Create test cases for Footer component', () => {
  test('It should match the snapshot', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <Footer />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
