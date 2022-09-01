import { render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import publicService from '../../../../../services/publicService';
import userService from '../../../../../services/userService';
import HomepageMentors from '../Mentors';

/* const notSignInData = {
  data: {
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
    ],
    totalPages: 2,
  },
  status: 200,
};
const signInData = {
  data: {
    content: [
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
  },
  status: 200,
};

describe('Create test cases for homepage mentors list', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <HomepageMentors handleButtonClick={() => null} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Check if the right data is displayed when not signed in', async () => {
    expect.hasAssertions();
    jest.spyOn(publicService, 'getPublicMentors').mockResolvedValue(notSignInData);
    render(
      <Provider store={store}>
        <HomepageMentors handleButtonClick={() => null} />
      </Provider>,
    );
    const mentorName = await screen.findByTestId('John Smith');
    expect(mentorName).toBeInTheDocument();
  });
  test('Check if the right data is displayed when signed in', async () => {
    expect.hasAssertions();
    jest.spyOn(userService, 'getMentors').mockResolvedValue(signInData);
    render(
      <Provider store={store}>
        <HomepageMentors handleButtonClick={() => null} />
      </Provider>,
    );
    const mentorName = await screen.findByTestId('Mark Smith');
    expect(mentorName).toBeInTheDocument();
  });
  test('Test fetching empty mentor list', async () => {
    expect.hasAssertions();
    jest.spyOn(publicService, 'getPublicMentors').mockResolvedValue({ data: { content: [], totalPages: 0 }, status: 200 });
    render(
      <Provider store={store}>
        <HomepageMentors handleButtonClick={() => null} />
      </Provider>,
    );
    const emptyCategoryHeading = await screen.findByTestId('emptyMentorMessage');
    expect(emptyCategoryHeading).toBeInTheDocument();
  });
});
*/
