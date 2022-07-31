import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import CourseMainContent from '../CourseMainContent';

const moduleInfo = {
  title: 'React js',
  moduleItemSet: [{ title: 'Redux' }],
};

describe('Create test cases for CourseMainContent', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <CourseMainContent
          description="This course talks about react"
          goal="The goal is to gain experience."
          modules={[moduleInfo]}
          mentorDto={{ name: 'john', surname: 'Smith', linkedinPath: 'myLinkedin.com' }}
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
