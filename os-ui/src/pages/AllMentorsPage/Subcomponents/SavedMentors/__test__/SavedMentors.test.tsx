import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
import { store } from '../../../../../redux/Store';
import SavedMentors from '../SavedMentors';
import mentorService from '../../../../../services/mentorService';

jest.mock('react-i18next', () => ({
  ...jest.requireActual('react-i18next'),
  useTranslation: () => ({
    t: (message:string) => {
      if (message === 'Empty Data Error Message') return 'No data to display';
      return 'Something went wrong please refresh the page';
    },
  }),
}));

jest.mock('redux-state-sync', () => ({
  createStateSyncMiddleware:
    () => () => (next: (action: PayloadAction) => void) => (action: PayloadAction) => next(action),
  initMessageListener: () => jest.fn(),
}));

describe('Create test cases for SavedMentors component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <SavedMentors />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Expect to have a No data to display message when there are no data from server', async () => {
    expect.hasAssertions();
    jest.spyOn(mentorService, 'requestUserSavedMentors').mockResolvedValue({ content: [] });
    render(
      <Provider store={store}>
        <SavedMentors />
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
        <SavedMentors />
      </Provider>,
    );
    const emptyMessageHeader = await screen.findByTestId('errorMessageHeader');
    expect(emptyMessageHeader).toBeInTheDocument();
    expect(emptyMessageHeader).toHaveTextContent('Something went wrong please refresh the page');
  });
});
