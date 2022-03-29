import {
  render, screen, waitFor,
} from '@testing-library/react';
import '@testing-library/jest-dom';
import userEvent from '@testing-library/user-event';
import Homepage from '../Homepage';
import { ERROR_MESSAGE } from '../../../constants/Strings';
import * as fetchCategory from '../../../services/getCategories';
import * as fetchMentor from '../../../services/getMentors';

const categoryData = {
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
    {
      title: 'C#',
      logoPath: 'https://reactjs.org/logo-og.png',
    },
    {
      title: 'Vue JS',
      logoPath: 'https://reactjs.org/logo-og.png',
    },
    {
      title: 'HTML',
      logoPath: 'https://reactjs.org/logo-og.png',
    },
    {
      title: 'CSS',
      logoPath: 'https://reactjs.org/logo-og.png',
    },
    {
      title: 'WEBPACK',
      logoPath: 'https://reactjs.org/logo-og.png',
    },
    {
      title: 'JEST',
      logoPath: 'https://reactjs.org/logo-og.png',
    },
  ],
  totalPages: 2,
};

const mentorData = {
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
    {
      name: 'Zack',
      surname: 'Smith',
      professionName: 'Angular Developer',
      companyName: 'Google',
      courseCount: 4,
      userImgPath: 'https://image.shutterstock.com/image-vector/businessman-avatar-profile-picture-260nw-221565274.jpg',
      emailPath: 'fakeEmail.com',
      linkedinPath: 'https://linkedin.com/feed',
    },
    {
      name: 'Emily',
      surname: 'Smith',
      professionName: 'Android Developer',
      companyName: 'Facebook',
      courseCount: 6,
      userImgPath: 'https://image.shutterstock.com/image-vector/businessman-avatar-profile-picture-260nw-221565274.jpg',
      emailPath: 'fakeEmail.com',
      linkedinPath: 'https://linkedin.com/feed',
    },
    {
      name: 'Ariane',
      surname: 'Smith',
      professionName: 'IOS Developer',
      companyName: 'Microsoft',
      courseCount: 17,
      userImgPath: 'https://image.shutterstock.com/image-vector/businessman-avatar-profile-picture-260nw-221565274.jpg',
      emailPath: 'fakeEmail.com',
      linkedinPath: 'https://linkedin.com/feed',
    },
    {
      name: 'Claire',
      surname: 'Smith',
      professionName: 'C++ Developer',
      companyName: 'Amazon',
      courseCount: 8,
      userImgPath: 'https://image.shutterstock.com/image-vector/businessman-avatar-profile-picture-260nw-221565274.jpg',
      emailPath: 'fakeEmail.com',
      linkedinPath: 'https://linkedin.com/feed',
    },

  ],
  totalPages: 2,
};

describe('Create several unit tests for Homepage Component', () => {
  test('Create a Snapshot test', () => {
    const { asFragment } = render(<Homepage />);
    expect(asFragment()).toMatchSnapshot();
  });
  test('Create a resolved promise test for course category', async () => {
    jest.spyOn(fetchCategory, 'getCategories').mockResolvedValue(categoryData);
    jest.spyOn(fetchMentor, 'getMentors').mockResolvedValue(mentorData);
    render(<Homepage />);
    await waitFor(() => {
      const categoryTitle1 = screen.queryByText('JavaScript');
      expect(categoryTitle1).toBeInTheDocument();
    });
  });
  test('Create a resolved Promise test with empty course categories', async () => {
    jest.spyOn(fetchCategory, 'getCategories').mockResolvedValue({ content: [], totalPages: 0 });
    jest.spyOn(fetchMentor, 'getMentors').mockResolvedValue(mentorData);
    render(<Homepage />);
    await waitFor(() => {
      const emptyCategoryHeading = screen.queryByText('We do not have courses yet');
      expect(emptyCategoryHeading).toBeInTheDocument();
    });
  });
  test('Test a resolved Promise for categories when clicking next group of courses', async () => {
    jest.spyOn(fetchCategory, 'getCategories').mockResolvedValue(categoryData);
    jest.spyOn(fetchMentor, 'getMentors').mockResolvedValue(mentorData);
    render(<Homepage />);
    const rightArrowButton = screen.getByTestId('categoryRightArrow');
    userEvent.click(rightArrowButton);
    await waitFor(() => {
      const categoryTitle2 = screen.queryByText('JEST');
      expect(categoryTitle2).toBeInTheDocument();
    });
  });
  test('Create a rejected Promise test for categories', async () => {
    jest.spyOn(fetchCategory, 'getCategories').mockResolvedValue({ errorMessage: ERROR_MESSAGE });
    jest.spyOn(fetchMentor, 'getMentors').mockResolvedValue(mentorData);
    render(<Homepage />);
    await waitFor(() => {
      const errorMessageHeading = screen.queryByTestId('categoriesErrorMessage');
      expect(errorMessageHeading).toBeInTheDocument();
    });
  });

  test('Create a resolved promise test for mentorList', async () => {
    jest.spyOn(fetchMentor, 'getMentors').mockResolvedValue(mentorData);
    jest.spyOn(fetchCategory, 'getCategories').mockResolvedValue(categoryData);
    render(<Homepage />);
    await waitFor(() => {
      const mentorName = screen.queryByText('John Smith');
      expect(mentorName).toBeInTheDocument();
    });
  });
  test('Create a resolved Promise test with empty mentor list', async () => {
    jest.spyOn(fetchMentor, 'getMentors').mockResolvedValue({ content: [], totalPages: 0 });
    jest.spyOn(fetchCategory, 'getCategories').mockResolvedValue(categoryData);
    render(<Homepage />);
    await waitFor(() => {
      const emptyMentorHeading = screen.queryByText('We do not have mentors yet');
      expect(emptyMentorHeading).toBeInTheDocument();
    });
  });
  test('Test a resolved Promise for mentors when clicking next group of courses', async () => {
    jest.spyOn(fetchMentor, 'getMentors').mockResolvedValue(mentorData);
    jest.spyOn(fetchCategory, 'getCategories').mockResolvedValue(categoryData);
    render(<Homepage />);
    const rightArrowButton = screen.getByTestId('mentorRightArrow');
    userEvent.click(rightArrowButton);
    await waitFor(() => {
      const mentorName = screen.queryByText('Claire Smith');
      expect(mentorName).toBeInTheDocument();
    });
  });
  test('Create a rejected Promise test for mentors list', async () => {
    jest.spyOn(fetchCategory, 'getCategories').mockResolvedValue(categoryData);
    jest.spyOn(fetchMentor, 'getMentors').mockResolvedValue({ errorMessage: ERROR_MESSAGE });
    render(<Homepage />);
    await waitFor(() => {
      const errorMessageHeading = screen.queryByTestId('mentorsErrorMessage');
      expect(errorMessageHeading).toBeInTheDocument();
    });
  });
});
