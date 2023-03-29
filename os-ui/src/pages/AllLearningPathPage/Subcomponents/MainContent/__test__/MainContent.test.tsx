import { render } from '@testing-library/react';
import i18next from 'i18next';
import { Suspense } from 'react';
import { initReactI18next } from 'react-i18next';
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router-dom';
import { store as myStore } from '../../../../../redux/Store';
import { reactRouterDomMock } from '../../../../AllMentorsPage/Subcomponents/MainContent/__test__/react-router-dom-mock';
import MainContent from '../MainContent';

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
      <Provider store={myStore}>
        <MemoryRouter>
          <Suspense fallback={<div>Loading...</div>}>
            <MainContent />
          </Suspense>
        </MemoryRouter>
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
