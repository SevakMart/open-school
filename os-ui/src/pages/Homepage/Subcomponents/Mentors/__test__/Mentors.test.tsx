import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { BrowserRouter as Router } from 'react-router-dom';
import { store } from '../../../../../redux/Store';
import HomepageMentors from '../Mentors';

jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));

describe('Create test cases for homepage mentors list', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Router>
        <Provider store={store}>
          <HomepageMentors />
        </Provider>
      </Router>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
