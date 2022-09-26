import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Provider } from 'react-redux';
import { PayloadAction } from '@reduxjs/toolkit';
import { store } from '../../../../../redux/Store';
import CheckedContent from '../CheckedContent';

const mockUseNavigate = jest.fn();
const mockUseLocation = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom') as any,
  useNavigate: () => mockUseNavigate,
  useLocation: () => mockUseLocation,
}));
jest.mock('redux-state-sync', () => ({
  createStateSyncMiddleware:
    () => () => (next: (action: PayloadAction) => void) => (action: PayloadAction) => next(action),
  initMessageListener: () => jest.fn(),
}));
describe('Create test cases for CheckedContent component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <CheckedContent
          id="1"
          checkedContent="React"
          filterFeature="category"
        />
      </Provider>,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Test if the content is rendered as expected', () => {
    expect.hasAssertions();
    render(
      <Provider store={store}>
        <CheckedContent
          id="1"
          checkedContent="React"
          filterFeature="category"
        />
      </Provider>,
    );
    const labelOfCheckedElement = screen.queryByTestId('label of React');
    expect(labelOfCheckedElement).toBeInTheDocument();
    expect(labelOfCheckedElement).toHaveTextContent('React');
  });
});
