import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
import i18next from 'i18next';
import { initReactI18next, I18nextProvider } from 'react-i18next';
import { Suspense } from 'react';
import ModuleItem from '../ModuleItem';
import { store } from '../../../../../redux/Store';

i18next.use(initReactI18next).init({
  lng: 'en',
  fallbackLng: 'en',
  interpolation: {
	  escapeValue: false,
  },
});

const moduleInfo = {
  title: 'React js',
  description: 'It talks about React',
  moduleItemSet: [{ title: 'Redux' }],
};
jest.mock('redux-state-sync', () => ({
  createStateSyncMiddleware:
    () => () => (next: (action: PayloadAction) => void) => (action: PayloadAction) => next(action),
  initMessageListener: () => jest.fn(),
}));

describe('Create test cases for ModuleItem component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <I18nextProvider i18n={i18next}>
          <Suspense fallback={<div>Loading...</div>}>
            <ModuleItem moduleInfo={moduleInfo} />
          </Suspense>
        </I18nextProvider>
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
