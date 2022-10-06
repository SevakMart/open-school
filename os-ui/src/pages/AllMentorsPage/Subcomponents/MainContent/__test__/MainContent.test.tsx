import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import MainContent from '../MainContent';

jest.mock('react-i18next', () => ({
  ...jest.requireActual('react-i18next'),
  useTranslation: () => ({
    t: (message:string) => {
      if (message === 'Empty Data Error Message') return 'No data to display';
      return 'Something went wrong please refresh the page';
    },
  }),
}));

describe('Create test cases for MainContent component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <MainContent />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
