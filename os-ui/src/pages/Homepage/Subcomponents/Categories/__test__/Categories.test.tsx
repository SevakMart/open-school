import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import HomepageCategories from '../Categories';
import { store } from '../../../../../redux/Store';

jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));

describe('Create test cases for homepage categories list', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <HomepageCategories />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
