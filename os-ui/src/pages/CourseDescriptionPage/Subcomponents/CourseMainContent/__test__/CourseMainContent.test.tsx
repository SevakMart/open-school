import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import CourseMainContent from '../CourseMainContent';

const moduleInfo = {
  title: 'React js',
  description: 'It talks about React',
  moduleItemSet: [{ title: 'Redux' }],
};
const mockUseNavigate = jest.fn();
const mockUseLocation = jest.fn(() => ({
  pathname: '/userCourse/modulOverview/123',
}));
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
  useLocation: () => mockUseLocation,
}));
jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));

describe('Create test cases for CourseMainContent', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <BrowserRouter>
          <CourseMainContent description="This course talks about react" goal="The goal is to gain experience." modules={[moduleInfo]} mentorDto={{ name: 'john', surname: 'Smith', linkedinPath: 'myLinkedin.com' }} title="React" currentUserEnrolled={false} enrolledCourseId={0} Course_Level="Intermediate" Estimated_efforts={1.9} />
        </BrowserRouter>
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
