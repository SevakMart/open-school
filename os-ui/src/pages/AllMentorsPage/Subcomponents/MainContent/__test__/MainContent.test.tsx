import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router-dom';
import { store } from '../../../../../redux/Store';
import MainContent from '../MainContent';
import { reactRouterDomMock } from './react-router-dom-mock';

jest.mock('react-i18next', () => ({
  ...jest.requireActual('react-i18next'),
  useTranslation: () => ({
    t: (message:string) => {
      if (message === 'Empty Data Error Message') return 'No data to display';
      return 'Something went wrong please refresh the page';
    },
  }),
}));

jest.doMock('react-router-dom', () => reactRouterDomMock);

describe('Create test cases for MainContent component', () => {
  test('Create a snapshot test', () => {
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
