import { render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import HomepageCategories from '../Categories';
import publicService from '../../../../../services/publicService';
import categoriesService from '../../../../../services/categoriesService';
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

describe('Create test cases for homepage mentors list', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <HomepageCategories />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Check if the right data is displayed when not signed in', async () => {
    expect.hasAssertions();
    jest.spyOn(publicService, 'getPublicCategories').mockResolvedValue(notSignInData);
    render(
      <Provider store={store}>
        <HomepageCategories />
      </Provider>,
    );
    const categoryTitle = await screen.findByTestId('JavaScript');
    expect(categoryTitle).toBeInTheDocument();
  });
  test('Check if the right data is displayed when signed in', async () => {
    expect.hasAssertions();
    jest.spyOn(categoriesService, 'getCategories').mockResolvedValue(signInData);
    render(
      <Provider store={store}>
        <HomepageCategories />
      </Provider>,
    );
    const categoryTitle = await screen.findByTestId('Angular');
    expect(categoryTitle).toBeInTheDocument();
  });
  test('Test fetching empty Categories list', async () => {
    jest.spyOn(publicService, 'getPublicCategories').mockResolvedValue({ data: { content: [], totalPages: 0 }, status: 200 });
    render(
      <Provider store={store}>
        <HomepageCategories />
      </Provider>,
    );
    const emptyCategoryHeading = await screen.findByTestId('emptyCategoryMessage');
    expect(emptyCategoryHeading).toBeInTheDocument();
  });
});
