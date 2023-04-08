import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router-dom';
import { store } from '../../../../../redux/Store';
import CourseSummary from '../CourseSummary';
import { reactRouterDomMock } from '../../../../AllMentorsPage/Subcomponents/MainContent/__test__/react-router-dom-mock';

jest.doMock('react-router-dom', () => reactRouterDomMock);
jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));
describe('Create test cases for course description page', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <MemoryRouter>
          <CourseSummary
            rating={4.5}
            enrolled={2}
            level="Advanced"
            language="French"
            duration={360}
            courseId={2}
            enrolledCourseId={2}
            userIdAndToken={{ id: 2, token: 'jksndfkjnskfk' }}
            title="React"
            currentUserEnrolled={false}
          />
        </MemoryRouter>
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
