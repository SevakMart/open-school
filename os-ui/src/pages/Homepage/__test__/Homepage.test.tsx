import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import Homepage from '../Homepage';
import publicService from '../../../services/publicService';

const categoryData1 = {
  content: [
    {
      title: 'JavaScript',
      logoPath: 'https://reactjs.org/logo-og.png',
    },
    {
      title: 'TypeScript',
      logoPath: 'https://reactjs.org/logo-og.png',
    },
    {
      title: 'React Js',
      logoPath: 'https://reactjs.org/logo-og.png',
    },
    {
      title: 'Angular',
      logoPath: 'https://reactjs.org/logo-og.png',
    },
    {
      title: 'Java',
      logoPath: 'https://reactjs.org/logo-og.png',
    },
    {
      title: 'C++',
      logoPath: 'https://reactjs.org/logo-og.png',
    },
  ],
  totalPages: 2,
};

const mentorData1 = {
  content: [
    {
      name: 'John',
      surname: 'Smith',
      professionName: 'JS Developer',
      companyName: 'Google',
      courseCount: 5,
      userImgPath: 'https://image.shutterstock.com/image-vector/businessman-avatar-profile-picture-260nw-221565274.jpg',
      emailPath: 'fakeEmail.com',
      linkedinPath: 'https://linkedin.com/feed',
    },
    {
      name: 'Jane',
      surname: 'Smith',
      professionName: 'React Developer',
      companyName: 'Facebook',
      courseCount: 16,
      userImgPath: 'https://image.shutterstock.com/image-vector/businessman-avatar-profile-picture-260nw-221565274.jpg',
      emailPath: 'fakeEmail.com',
      linkedinPath: 'https://linkedin.com/feed',
    },
    {
      name: 'Jake',
      surname: 'Smith',
      professionName: 'C# Developer',
      companyName: 'Microsoft',
      courseCount: 10,
      userImgPath: 'https://image.shutterstock.com/image-vector/businessman-avatar-profile-picture-260nw-221565274.jpg',
      emailPath: 'fakeEmail.com',
      linkedinPath: 'https://linkedin.com/feed',
    },
    {
      name: 'Mark',
      surname: 'Smith',
      professionName: 'NodeJS Developer',
      companyName: 'Amazon',
      courseCount: 7,
      userImgPath: 'https://image.shutterstock.com/image-vector/businessman-avatar-profile-picture-260nw-221565274.jpg',
      emailPath: 'fakeEmail.com',
      linkedinPath: 'https://linkedin.com/feed',
    },
  ],
  totalPages: 2,
};

describe('Create several unit tests for Homepage Component', () => {
  test('Create a Snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <Homepage />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Test Fetching Categories list from database when not signed in', async () => {
    jest.spyOn(publicService, 'getPublicMentors').mockResolvedValue(mentorData1);
    jest.spyOn(publicService, 'getPublicCategories').mockResolvedValue(categoryData1);
    render(
      <Provider store={store}>
        <Homepage />
      </Provider>,
    );
    const categoryTitle = await screen.findByTestId('JavaScript');
    expect(categoryTitle).toBeInTheDocument();
  });
  test('Test fetching empty Categories list', async () => {
    jest.spyOn(publicService, 'getPublicMentors').mockResolvedValue(mentorData1);
    jest.spyOn(publicService, 'getPublicCategories').mockResolvedValue({ content: [], totalPages: 0 });
    render(
      <Provider store={store}>
        <Homepage />
      </Provider>,
    );
    const emptyCategoryHeading = await screen.findByTestId('emptyCategoryMessage');
    expect(emptyCategoryHeading).toBeInTheDocument();
  });

  test('Test Fetching Mentors list from database', async () => {
    jest.spyOn(publicService, 'getPublicMentors').mockResolvedValue(mentorData1);
    jest.spyOn(publicService, 'getPublicCategories').mockResolvedValue(categoryData1);
    render(
      <Provider store={store}>
        <Homepage />
      </Provider>,
    );
    const mentorName = await screen.findByTestId('John Smith');
    expect(mentorName).toBeInTheDocument();
  });
  test('Test fetching empty mentors list', async () => {
    jest.spyOn(publicService, 'getPublicMentors').mockResolvedValue({ content: [], totalPages: 0 });
    jest.spyOn(publicService, 'getPublicCategories').mockResolvedValue(categoryData1);
    render(
      <Provider store={store}>
        <Homepage />
      </Provider>,
    );
    const emptyMentorHeading = await screen.findByTestId('emptyMentorMessage');
    expect(emptyMentorHeading).toBeInTheDocument();
  });
});
