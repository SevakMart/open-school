import { Provider } from 'react-redux';
import { render, screen } from '@testing-library/react';
import { debug } from 'console';
import InProgressCourse from '../InProgressCourse';
import { store } from '../../../../../redux/Store';

jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));

const mockedUseNavigate = jest.fn();

jest.mock('react-router-dom', () => ({
  ...(jest.requireActual('react-router-dom') as any),
  useNavigate: () => mockedUseNavigate,
}));

describe('Create test cases for InProgress component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <InProgressCourse
          title="React js"
          courseStatus="In_Progress"
          percentage={47}
          remainingTime={90}
          dueDate="2022-05-07"
          courseId={0}
          id={0}
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });

  test('Check if the content is as expected', () => {
    render(
      <Provider store={store}>
        <InProgressCourse
          title="React js"
          courseStatus="In_Progress"
          percentage={47}
          remainingTime={90}
          dueDate="2022-05-07"
          courseId={0}
          id={0}
        />
      </Provider>,
    );

    const courseTitleElement = screen.getByText('React js');
    const statusContentElement = screen.getByText('In_Progress');
    const percentageContentElement = screen.getByText('47%');

    debug();

    const remainingTimeContentElement = screen.getByText('90 minutes');
    const dueDateContentElement = screen.getByText('2022-05-07');

    expect(courseTitleElement).toBeInTheDocument();
    expect(statusContentElement).toBeInTheDocument();
    expect(percentageContentElement).toBeInTheDocument();
    expect(remainingTimeContentElement).toBeInTheDocument();
    expect(dueDateContentElement).toBeInTheDocument();
  });
});
