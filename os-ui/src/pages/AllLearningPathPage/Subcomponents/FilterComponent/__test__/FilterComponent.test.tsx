import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import FilterComponent from '../FilterComponent';

jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));

describe('Create test cases for FilterComponent component', () => {
  test('Create a snapshot tets', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <FilterComponent />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
