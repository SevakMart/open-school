import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import Title from '../Title';

jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));

describe('Create test cases for title component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <Title
          mainTitle="Mentors"
          subTitle="Saved Mentors"
          isMentor
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
