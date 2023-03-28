import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import i18next from 'i18next';
import { initReactI18next, I18nextProvider } from 'react-i18next';
import { Suspense } from 'react';
import MainContent from '../MainContent';
import { store } from '../../../../../redux/Store';

const mockUseNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
  useLocation: () => ({
    search: '',
  }),
}));

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
        <I18nextProvider i18n={i18next}>
          <Suspense fallback={<div>Loading...</div>}>
            <MainContent />
          </Suspense>
        </I18nextProvider>
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
