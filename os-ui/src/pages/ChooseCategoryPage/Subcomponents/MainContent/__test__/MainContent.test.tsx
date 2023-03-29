import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router-dom';
import { store } from '../../../../../redux/Store';
import { reactRouterDomMock } from '../../../../AllMentorsPage/Subcomponents/MainContent/__test__/react-router-dom-mock';
import MainContent from '../MainContent';

jest.doMock('react-router-dom', () => reactRouterDomMock);
jest.mock('react-i18next', () => ({
  ...jest.requireActual('react-i18next'),
  useTranslation: () => ({
    t: (message:string) => {
      if (message === 'error') return 'Something went wrong please refresh the page';
    },
  }),
}));

describe('Create test case to MainContent component', () => {
  test('Create a snapshot test', async () => {
    const { asFragment } = render(
      <Provider store={store}>
        <MemoryRouter>
          <MainContent
            isLoading={false}
            errorMessage=""
            searchedCategories={{ JavaScript: [{ id: 1, title: 'React' }] }}
          />
        </MemoryRouter>
      </Provider>,
    );
    expect(asFragment).toMatchSnapshot();
  });
});
