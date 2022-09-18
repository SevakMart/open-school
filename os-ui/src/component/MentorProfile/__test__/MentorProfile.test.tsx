import { render, screen } from '@testing-library/react';
import MentorCard from '../MentorProfile';

/* const MentorProfile = {
  name: 'John',
  surname: 'Smith',
  professionName: 'JS Developer',
  companyName: 'Google',
  courseCount: 5,
  userImgPath: 'https://image.shutterstock.com/image-vector/businessman-avatar-profile-picture-260nw-221565274.jpg',
  emailPath: 'fakeEmail.com',
  linkedinPath: 'https://linkedin.com/feed',
};

describe('Make unit tests on Mentor Profile', () => {
  test('Make a snapshot test', () => {
    const { asFragment } = render(<MentorCard mentor={{ ...MentorProfile }} />);
    expect(asFragment()).toMatchSnapshot();
  });
  test('Verify if Mentor info is contained in the component', () => {
    render(<MentorCard mentor={{ ...MentorProfile }} />);
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
*/
