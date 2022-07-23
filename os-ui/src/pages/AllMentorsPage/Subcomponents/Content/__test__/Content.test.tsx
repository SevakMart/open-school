import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import Content from '../Content';
import mentorService from '../../../../../services/mentorService';

const mockUseNavigate = jest.fn();
jest.mock('react-i18next', () => ({
  ...jest.requireActual('react-i18next'),
  useTranslation: () => ({
    t: (message:string) => {
      if (message === 'messages.noData.default') return 'No data to display';
      return 'Something went wrong please refresh the page';
    },
  }),
}));

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useLocation: () => ({
    search: '',
  }),
  useNavigate: () => mockUseNavigate,
}));

describe('Create test cases for Content component', () => {
  test('Create a snapshot test for Content component', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <Content />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Expect to have a No data to display message when there are no data from server', async () => {
    expect.hasAssertions();
    jest.spyOn(mentorService, 'requestUserSavedMentors').mockResolvedValue({ content: [] });
    jest.spyOn(mentorService, 'requestAllMentors').mockResolvedValue({ content: [] });
    render(
      <Provider store={store}>
        <Content />
      </Provider>,
    );
    const emptyMessageHeader = await screen.findByTestId('emptyMessageHeader');
    expect(emptyMessageHeader).toBeInTheDocument();
    expect(emptyMessageHeader).toHaveTextContent('No data to display');
  });
  test('Expect to display an error message when fetching data is rejected', async () => {
    expect.hasAssertions();
    jest.spyOn(mentorService, 'requestUserSavedMentors').mockRejectedValue({ error: 'Oops' });
    render(
      <Provider store={store}>
        <Content />
      </Provider>,
    );
    const emptyMessageHeader = await screen.findByTestId('errorMessageHeader');
    expect(emptyMessageHeader).toBeInTheDocument();
    expect(emptyMessageHeader).toHaveTextContent('Something went wrong please refresh the page');
  });
});
