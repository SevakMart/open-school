import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../redux/Store';
import CourseModulePage from '../CourseModulePage';

/* eslint-disable max-len */

const userInfo = {
  token: 'fake token',
  id: 1,
};
jest.mock('react-i18next', () => ({
  useTranslation: () => ({ t: (key:string) => key }),
}));

describe('Create test cases for course overview page', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}><CourseModulePage userInfo={userInfo} /></Provider>,
	 { wrapper: BrowserRouter },
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
