import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import SuggestedCourses from '../SuggestedCourses';

jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));

describe('Create several unit tests for SuggestedCourses Component', () => {
  test('Create a Snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <SuggestedCourses
          userId={1}
          token="123"
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
