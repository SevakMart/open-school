import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router-dom';
import { store } from '../../../../../redux/Store';
import { reactRouterDomMock } from '../../../../AllMentorsPage/Subcomponents/MainContent/__test__/react-router-dom-mock';

import MainContent from '../MainContent';
import { store } from '../../../../../redux/Store';

jest.doMock('react-router-dom', () => reactRouterDomMock);

i18next.use(initReactI18next).init({
  lng: 'en',
  fallbackLng: 'en',
  interpolation: {
    escapeValue: false,
  },
});

describe('Create test cases for CourseContentHeader', () => {
  test('Make a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <MemoryRouter>
          <MainContent />
        </MemoryRouter>
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
