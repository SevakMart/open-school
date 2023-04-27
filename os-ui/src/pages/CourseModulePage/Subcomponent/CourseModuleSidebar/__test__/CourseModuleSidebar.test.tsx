import { render, screen, fireEvent } from '@testing-library/react';
import { Provider } from 'react-redux';
import { createStore } from '@reduxjs/toolkit';
import { BrowserRouter } from 'react-router-dom';
import rootReducer from '../../../../../redux/RootReducer';
import CourseModuleSidebar from '../CourseModuleSidebar';

describe('CourseModuleSidebar', () => {
  const store = createStore(rootReducer);

  test('renders the component', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <BrowserRouter>
          <CourseModuleSidebar />
        </BrowserRouter>
      </Provider>,
    );
    expect(screen.getByText('Overview')).toBeInTheDocument();
    expect(asFragment()).toMatchSnapshot();
  });

  test('toggles the module list on click', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <BrowserRouter>
          <CourseModuleSidebar />
        </BrowserRouter>
      </Provider>,
    );
    fireEvent.click(screen.getByAltText('chevron'));
    expect(asFragment()).toMatchSnapshot();

    fireEvent.click(screen.getByAltText('chevron'));
    expect(asFragment()).toMatchSnapshot();
  });

  test('calls handleChangeValue when clicking the Overview button', () => {
    const { asFragment } = render(
      <Provider store={store}>
        <BrowserRouter>
          <CourseModuleSidebar />
        </BrowserRouter>
      </Provider>,
    );
    fireEvent.click(screen.getByText('Overview'));
    expect(asFragment()).toMatchSnapshot();
  });
});
