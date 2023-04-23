import { createStore } from '@reduxjs/toolkit';
import {
  act, fireEvent, render, screen,
} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { unmountComponentAtNode } from 'react-dom';
import { Provider } from 'react-redux';
import rootReducer from '../../../../../../redux/RootReducer';
import AskQuestionPopup from '../AskQuestionPopup';

describe('AskQuestionPopup', () => {
  const handleClose = jest.fn();
  const handleChange = jest.fn();
  const cleanTextField = jest.fn();

  const store = createStore(rootReducer);

  let container: Element | null = null;
  beforeEach(() => {
    container = document.createElement('div');
    document.body.appendChild(container);
    jest.useFakeTimers();
  });

  afterEach(() => {
    if (container) {
      unmountComponentAtNode(container);
      if (container instanceof Element) {
        container.remove();
      }
      container = null;
      jest.useRealTimers();
    }
  });

  test('input question', () => {
    render(
      <Provider store={store}>
        <AskQuestionPopup
          value=""
          isOpen
          handleChange={handleChange}
          handleClose={handleClose}
          enrolledCourseId={0}
          token="123"
          cleanTextField={cleanTextField}
          sectionName="Mentor"
        />
      </Provider>,
    );

    const closeButton = screen.getByTestId('close-btn');
    act(() => {
      userEvent.click(closeButton);
      jest.runAllTimers();
    });

    expect(handleClose).toHaveBeenCalled();

    const textArea = screen.getByTestId('question-textarea');
    const postButton = screen.getByTestId('post-btn');

    act(() => {
      fireEvent.input(textArea, { target: { value: '' } });
      userEvent.click(postButton);
    });

    const close_btn_x = screen.getByTestId('close-x-btn');
    act(() => {
      userEvent.click(close_btn_x);
      jest.runAllTimers();
    });
    expect(handleClose).toHaveBeenCalled();

    const close_cancel_btn = screen.getByTestId('close-cancel-btn');
    act(() => {
      userEvent.click(close_cancel_btn);
      jest.runAllTimers();
    });
    expect(handleClose).toHaveBeenCalled();
  });
});
