import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import MyLearningPathMainContent from '../MainContent';

describe('Create several unit tests for MyLearningPathMainContent Component', () => {
  test('Create a Snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <MyLearningPathMainContent />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
