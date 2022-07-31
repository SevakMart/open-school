import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { store } from '../../../../../redux/Store';
import ModuleItem from '../ModuleItem';

const moduleInfo = {
  title: 'React js',
  moduleItemSet: [{ title: 'Redux' }],
};

describe('Create test cases for ModuleItem component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <ModuleItem moduleInfo={moduleInfo} />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
