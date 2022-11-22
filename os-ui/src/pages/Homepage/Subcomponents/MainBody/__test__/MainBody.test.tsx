import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { BrowserRouter as Router } from 'react-router-dom';
import { store } from '../../../../../redux/Store';
import MainBody from '../MainBody';

jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));

describe('Create test cases for MainBody component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Router>
        <Provider store={store}>
          <MainBody
            page={0}
            maxPage={10}
            children={<p>Hello world</p>}
            clickNext={jest.fn()}
            clickPrevious={jest.fn()}
            isMentor={false}
          />
        </Provider>
      </Router>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
