import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../../../redux/Store';
import ModalMessageComponent from '../ModalMessageComponent';

describe('Create test cases for ModalMessageComponent', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <ModalMessageComponent enrollInCourse={() => null} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
