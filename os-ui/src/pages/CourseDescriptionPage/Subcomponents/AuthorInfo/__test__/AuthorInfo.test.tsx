import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { BrowserRouter as Router } from 'react-router-dom';
import { store } from '../../../../../redux/Store';
import AuthorInfo from '../AuthorInfo';

const mockUseNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
}));
jest.mock('react-i18next', () => ({
  ...jest.requireActual('react-i18next'),
  useTranslation: () => ({
    t: (message:string) => {
      if (message === 'error') return 'Something went wrong please refresh the page';
    },
  }),
}));

describe('Create test case to AuthorInfo component', () => {
  test('Create a snapshot test', async () => {
    const { asFragment } = render(
      <Provider store={store}>
        <Router>
          <AuthorInfo
            mentorName="John"
            mentorSurname="Smith"
            mentorLinkedIn="https://www.linkedin.com/in/john-smith/"
          />
        </Router>
      </Provider>,
    );
    expect(asFragment).toMatchSnapshot();
  });
});
