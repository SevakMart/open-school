import { render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import MentorCard from '../MentorProfile';

const mockUseNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
  useLocation: () => ({
    search: '',
  }),
}));

const MentorProfile = {
  name: 'John',
  surname: 'Smith',
  professionName: 'JS Developer',
  companyName: 'Google',
  courseCount: 5,
  userImgPath: 'https://image.shutterstock.com/image-vector/businessman-avatar-profile-picture-260nw-221565274.jpg',
  emailPath: 'fakeEmail.com',
  linkedinPath: 'https://linkedin.com/feed',
};
/* eslint-disable max-len */
describe('Make unit tests on Mentor Profile', () => {
  test('Make a snapshot test', () => {
    const { asFragment } = render(<Provider store={store}><MentorCard mentor={{ ...MentorProfile }} isHomepageNotSignedMentorCard /></Provider>);
    expect(asFragment()).toMatchSnapshot();
  });
  test('Verify if Mentor info is contained in the component', () => {
    render(<Provider store={store}><MentorCard mentor={{ ...MentorProfile }} isHomepageNotSignedMentorCard /></Provider>);
    const fullname = screen.queryByTestId('John Smith');
    const professionNameElem = screen.queryByTestId('JS Developer');
    const companyNameElem = screen.queryByTestId('Google');
    const courseCountElem = screen.queryByTestId('5');
    expect(fullname).toBeInTheDocument();
    expect(fullname).toHaveTextContent('John Smith');
    expect(professionNameElem).toBeInTheDocument();
    expect(professionNameElem).toHaveTextContent('JS Developer');
    expect(companyNameElem).toBeInTheDocument();
    expect(companyNameElem).toHaveTextContent('Google');
    expect(courseCountElem).toBeInTheDocument();
    expect(courseCountElem).toHaveTextContent('5');
  });
});
