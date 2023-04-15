import {
  act, fireEvent, render, screen,
} from '@testing-library/react';
import React from 'react';
import QuestionItem from '../QuestionItem';
import QuestionItemPopup from '../QuestionItemPopup/QuestionItemPopup';

describe('AskQuestionPopup', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  const fakeData = {
    id: 593,
    text: 'Test question',
    userDto: {
      id: 34, name: 'ed', surname: 'x', email: 'ed@gmail.com', professionName: null, companyName: null,
    },
    createdDate: '2023-04-13T15:42:35.444615700Z',
    courseDto: {
      id: 47, title: 'NODE.JS', rating: 0.0, difficulty: 'Basic', keywords: ['Web Design'],
    },
  };

  const urlAddQ = 'http://localhost:3000/api/v1/courses/enrolled/41/peers-questions';
  const urlEditQ = ' http://localhost:3000/api/v1/courses/enrolled/41/peers-questions/595';
  const urlDelQ = ' http://localhost:3000/api/v1/courses/enrolled/41/peers-questions/1';

  const removeQ = jest.fn();
  const questionChanged = jest.fn();

  const date = new Date(fakeData.createdDate);
  const formattedDate = date.toLocaleString('en-US', {
    month: 'long',
    day: 'numeric',
    year: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
    second: 'numeric',
    // timeZoneName: 'short',
  });

  test('post question', async () => {
    jest.spyOn(global, 'fetch').mockImplementation((url, options) => {
      expect(url).toBe(`${urlAddQ}`);
      expect(options?.method).toBe('POST');
      expect(options?.body).toBe(JSON.stringify({ text: 'Test question' }));

      const fakeResponse = new Response(JSON.stringify(fakeData), {
        status: 200,
        headers: {
          'Content-type': 'application/json',
        },
      });

      return Promise.resolve(fakeResponse);
    });

    await act(async () => {
      render(
        <QuestionItem
          removeQ={removeQ}
          questionChanged={questionChanged}
          text="Test question"
          id="1"
          createdDate="2023-04-13T15:42:35.444615700Z"
        />,
      );
    });

    const question_text = screen.getByTestId('questionItem-text');
    expect(question_text.innerHTML).toBe(fakeData.text);
    const question_date = screen.getByTestId('questionItem-date');
    expect(question_date.innerHTML).toBe(formattedDate);
  });

  test('edit question', async () => {
    jest.spyOn(global, 'fetch').mockImplementation((url, options) => {
      expect(url).toBe(`${urlEditQ}`);
      expect(options?.method).toBe('PUT');
      expect(options?.body).toBe(JSON.stringify({ text: 'Test question' }));

      const fakeResponse = new Response(JSON.stringify(fakeData), {
        status: 200,
        headers: {
          'Content-type': 'application/json',
        },
      });

      return Promise.resolve(fakeResponse);
    });

    await act(async () => {
      render(
        <QuestionItem
          removeQ={removeQ}
          questionChanged={questionChanged}
          text="Test question"
          id="1"
          createdDate="2023-04-13T15:42:35.444615700Z"
        />,
      );
    });

    const question_text = screen.getByTestId('questionItem-text');
    expect(question_text.innerHTML).toBe(fakeData.text);
  });

  test('delete question', async () => {
    jest.spyOn(global, 'fetch').mockImplementation((url, options) => {
      expect(url).toBe(`${urlDelQ}`);
      expect(options?.method).toBe('DELETE');

      const fakeResponse = new Response('', {
        status: 204,
      });

      return Promise.resolve(fakeResponse);
    });

    await act(async () => {
      render(
        <QuestionItem
          removeQ={removeQ}
          questionChanged={questionChanged}
          text="Test question"
          id="1"
          createdDate="2023-04-13T15:42:35.444615700Z"
        />,
      );
    });

    const animatedFunction = jest.fn();
    const onClose = jest.fn();
    const editQuestion = jest.fn();

    const mockRef = { current: null };
    const spy = jest.spyOn(React, 'useRef').mockReturnValueOnce(mockRef);

    await act(async () => {
      render(
        <QuestionItemPopup
          isOpen
          isAnimating
          animatedFunction={animatedFunction}
          onClose={onClose}
          editQuestion={editQuestion}
          btnType="save"
          btnTextType="Save"
          removeQ={removeQ}
          id="1"
          textAreaRef={mockRef}
        />,
      );
    });

    const delete_button = screen.getByTestId('remove-btn');
    fireEvent.click(delete_button);
    expect(removeQ).toHaveBeenCalledWith('1');
    spy.mockRestore();
  });
});
