import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router-dom';
import { store } from '../../../redux/Store';
import MyLearningPathPage from '../MyLearningPathPage';
import { reactRouterDomMock } from '../../AllMentorsPage/Subcomponents/MainContent/__test__/react-router-dom-mock';

const userInfo = {
  token: '123',
  id: 1,
};
jest.doMock('react-router-dom', () => reactRouterDomMock);
jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));

describe('Create several unit tests for MyLearningPathPage Component', () => {
  test('Create a Snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <MemoryRouter>
          <MyLearningPathPage userInfo={userInfo} />
        </MemoryRouter>
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
