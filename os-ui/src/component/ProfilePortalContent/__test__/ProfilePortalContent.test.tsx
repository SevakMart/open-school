import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import ProfilePortalContent from '../ProfilePortalContent';

const mockUseNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
  useLocation: () => ({
    search: '',
  }),
}));

describe('Create unit tests for ProfilePortalContent component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <ProfilePortalContent
          isSignOut={false}
          children={<p><span>Hello world</span></p>}
          icon={<p>{'>'}</p>}
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
