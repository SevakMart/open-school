import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { BrowserRouter as Router } from 'react-router-dom';
import { store } from '../../../../redux/Store';
import Header from '../Header';

jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));

describe('Create unit tests for Header component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Router>
        <Provider store={store}>
          <Header
            mainTitle="This is main Title"
            shouldRemoveIconContent={false}
            isForgotPasswordContent={false}
            isVerificationContent={false}
          />
        </Provider>
      </Router>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
