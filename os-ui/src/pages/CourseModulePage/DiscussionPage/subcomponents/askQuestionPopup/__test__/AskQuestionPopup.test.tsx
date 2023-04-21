import {
  act, fireEvent, render, screen,
} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { unmountComponentAtNode } from 'react-dom';
import AskQuestionPopup from '../AskQuestionPopup';

describe('AskQuestionPopup', () => {
  const onClose = jest.fn();
  const handleChange = jest.fn();
  const addQuestion = jest.fn();

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

  // test('input questiom', () => {
  //   render(<AskQuestionPopup
  //     value=""
  //     isOpen
  //     onClose={onClose}
  //     handleChange={handleChange}
  //     addQuestion={addQuestion}
  //   />);

  //   const closeButton = screen.getByTestId('close-btn');
  //   act(() => {
  //     userEvent.click(closeButton);
  //     jest.runAllTimers();
  //   });

  //   expect(onClose).toHaveBeenCalled();

  //   const textArea = screen.getByTestId('question-textarea');
  //   const postButton = screen.getByTestId('post-btn');

  //   act(() => {
  //     fireEvent.input(textArea, { target: { value: '' } });
  //     userEvent.click(postButton);
  //   });

  //   expect(addQuestion).not.toBeCalled();

  //   const close_btn_x = screen.getByTestId('close-x-btn');
  //   act(() => {
  //     userEvent.click(close_btn_x);
  //     jest.runAllTimers();
  //   });
  //   expect(onClose).toHaveBeenCalled();

  //   const close_cancel_btn = screen.getByTestId('close-cancel-btn');
  //   act(() => {
  //     userEvent.click(close_cancel_btn);
  //     jest.runAllTimers();
  //   });
  //   expect(onClose).toHaveBeenCalled();
  // });

  // test('should call addQuestion when post button is clicked and value is not empty, then post it', () => {
  //   render(<AskQuestionPopup
  //     value="new test value"
  //     isOpen
  //     onClose={onClose}
  //     handleChange={handleChange}
  //     addQuestion={addQuestion}
  //   />);

  //   const textArea = screen.getByTestId('question-textarea');
  //   const postButton = screen.getByTestId('post-btn');
  //   act(() => {
  //     userEvent.click(postButton);
  //   });

  //   expect(addQuestion).not.toBeCalled();

  //   act(() => {
  //     fireEvent.input(textArea, { target: { value: 'new test value' } });
  //     userEvent.click(postButton);
  //     jest.runAllTimers();
  //   });
  //   expect(addQuestion).toBeCalledWith('new test value');
  // });
});
