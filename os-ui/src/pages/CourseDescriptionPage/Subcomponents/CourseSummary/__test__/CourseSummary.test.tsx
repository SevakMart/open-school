import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import CourseSummary from '../CourseSummary';

const mockUseNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
  useLocation: () => ({
    search: '',
  }),
}));
jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));
describe('Create test cases for course description page', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <CourseSummary
          rating={4.5}
          enrolled={2}
          level="Advanced"
          language="French"
          duration={360}
          courseId={2}
          userIdAndToken={{ id: 2, token: 'jksndfkjnskfk' }}
          title="React"
          currentUserEnrolled={false}
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
