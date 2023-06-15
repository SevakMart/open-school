import React from 'react';
import ReactDOM from 'react-dom';
import './index.scss';

// fonts
import './fonts/Montserrat/Montserrat-Black.ttf';
import './fonts/Montserrat/Montserrat-BlackItalic.ttf';
import './fonts/Montserrat/Montserrat-Bold.ttf';
import './fonts/Montserrat/Montserrat-BoldItalic.ttf';
import './fonts/Montserrat/Montserrat-ExtraBold.ttf';
import './fonts/Montserrat/Montserrat-ExtraBoldItalic.ttf';
import './fonts/Montserrat/Montserrat-ExtraLight.ttf';
import './fonts/Montserrat/Montserrat-ExtraLightItalic.ttf';
import './fonts/Montserrat/Montserrat-Italic.ttf';
import './fonts/Montserrat/Montserrat-Light.ttf';
import './fonts/Montserrat/Montserrat-LightItalic.ttf';
import './fonts/Montserrat/Montserrat-Medium.ttf';
import './fonts/Montserrat/Montserrat-MediumItalic.ttf';
import './fonts/Montserrat/Montserrat-Regular.ttf';
import './fonts/Montserrat/Montserrat-SemiBold.ttf';
import './fonts/Montserrat/Montserrat-SemiBoldItalic.ttf';
import './fonts/Montserrat/Montserrat-Thin.ttf';
import './fonts/Montserrat/Montserrat-ThinItalic.ttf';

import { Provider } from 'react-redux';
import { I18nextProvider, initReactI18next } from 'react-i18next';
import i18next from 'i18next';
import I18NextHttpBackend from 'i18next-http-backend';
import I18nextBrowserLanguageDetector from 'i18next-browser-languagedetector';
import { store } from './redux/Store';
import App from './App';

i18next
  .use(I18NextHttpBackend)
  .use(I18nextBrowserLanguageDetector)
  .use(initReactI18next)
  .init({
    fallbackLng: 'en',
    debug: true,

    interpolation: {
      escapeValue: false,
    },
  });

ReactDOM.render(
  <React.StrictMode>
    <I18nextProvider i18n={i18next}>
      <Provider store={store}>
        <App />
      </Provider>
    </I18nextProvider>
  </React.StrictMode>,
  document.getElementById('root'),
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
