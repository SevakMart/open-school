import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import HomepageCategories from '../Categories';
import { store } from '../../../../../redux/Store';

const notSignInData = {
  data: {
    content: [
      {
        title: 'JavaScript',
        logoPath: 'https://reactjs.org/logo-og.png',
      },
      {
        title: 'TypeScript',
        logoPath: 'https://reactjs.org/logo-og.png',
      },
    ],
    totalPages: 2,
  },
  status: 200,
};
const signInData = {
  data: {
    content: [
      {
        title: 'React js',
        logoPath: 'https://reactjs.org/logo-og.png',
      },
      {
        title: 'Angular',
        logoPath: 'https://reactjs.org/logo-og.png',
      },
    ],
    totalPages: 2,
  },
  status: 200,
};

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
