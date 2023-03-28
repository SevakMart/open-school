import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import i18next from 'i18next';
import { initReactI18next, I18nextProvider } from 'react-i18next';
import { Suspense } from 'react';
import HomepageHeader from '../Header';
import { store } from '../../../../../redux/Store';

i18next.use(initReactI18next).init({
  lng: 'en',
  fallbackLng: 'en',
  interpolation: {
	  escapeValue: false,
  },
});

describe('Create tests for HomepageHeader', () => {
  test('Create snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <I18nextProvider i18n={i18next}>
          <Suspense fallback={<div>Loading...</div>}>
            <HomepageHeader />
          </Suspense>
        </I18nextProvider>
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
