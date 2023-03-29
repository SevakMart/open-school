import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router-dom';
import { store as myStore } from '../../../../../redux/Store';
import { reactRouterDomMock } from '../../../../AllMentorsPage/Subcomponents/MainContent/__test__/react-router-dom-mock';
import MainContent from '../MainContent';

jest.doMock('react-router-dom', () => reactRouterDomMock);

describe('Create test cases for CourseContentHeader', () => {
  test('Make a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={myStore}>
        <MemoryRouter>
          <MainContent />
        </MemoryRouter>
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
