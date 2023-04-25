import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
import { MemoryRouter } from 'react-router-dom';
import { store } from '../../../../../redux/Store';
import AllMentorsPageHeader from '../AllMentorsPageHeader';
import { reactRouterDomMock } from '../../MainContent/__test__/react-router-dom-mock';

jest.doMock('react-router-dom', () => reactRouterDomMock);

jest.mock('react-i18next', () => ({
  ...jest.requireActual('react-i18next'),
  useTranslation: () => ({
    t: (message:string) => {
      if (message === 'All Mentors') return 'All Mentors';
      return 'Saved Mentors';
    },
  }),
}));
jest.mock('redux-state-sync', () => ({
  createStateSyncMiddleware:
    () => () => (next: (action: PayloadAction) => void) => (action: PayloadAction) => next(action),
  initMessageListener: () => jest.fn(),
}));

describe('Create test cases for AllMentorsPageHeader component', () => {
  test('Create a snapshot test for AllMentorsHeader component', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <MemoryRouter>
          <AllMentorsPageHeader
            changeHeaderFocus={jest.fn()}
          />
        </MemoryRouter>
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
