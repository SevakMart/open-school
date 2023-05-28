import { createStore } from '@reduxjs/toolkit';
import {
  act, fireEvent, render, screen,
} from '@testing-library/react';
import React from 'react';
import { Provider } from 'react-redux';
import rootReducer from '../../../../../../redux/RootReducer';
import { formatDate } from '../../../helpers/formatDate';
import QuestionItem from '../QuestionItem';
import QuestionItemPopup from '../subcomponents/QuestionItemPopup/QuestionItemPopup';

const url_Q = '/api/v1/courses/enrolled/41/peers-questions';
const fakeResponsesMap = [
  {
    questionId: {
      idAnswer: '233',
      answerText: 'Answer1',
      answerCreatedDate: '2023-05-02T06:02:08.535262100Z',
      name: 'ed',
      surname: 'x',
      questionId: '971',
    },
  },
  {
    questionId: {
      idAnswer: '217',
      answerText: 'Answer2',
      answerCreatedDate: '2023-05-02T06:02:09.535262100Z',
      name: 'ed',
      surname: 'x',
      questionId: '970',
    },
  },
];

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

// const date = new Date(fakeData.createdDate);
const formattedDate = formatDate(fakeData.createdDate);

const fakeResponse = new Response(JSON.stringify(fakeData), {
  status: 200,
  headers: {
    'Content-type': 'application/json',
  },
});

describe('AskQuestionPopup', () => {
  const removeQ = jest.fn();
  const store = createStore(rootReducer);

  beforeEach(() => {
    render(
      <Provider store={store}>
        <>
          <QuestionItem
            text="Test question"
            id="1"
            createdDate="2023-04-13T15:42:35.444615700Z"
            token=""
            enrolledCourseId={28}
            sectionName="Peers"
            responsesMap={fakeResponsesMap}
            name="joe"
            surname="Tribiani"
          />
        </>
      </Provider>,
    );
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  test('post question', async () => {
    jest.spyOn(global, 'fetch').mockImplementation((url, options) => {
      expect(url).toBe(`${url_Q}`);
      expect(options?.method).toBe('POST');
      expect(options?.body).toBe(JSON.stringify({ text: 'Test question' }));
      return Promise.resolve(fakeResponse);
    });

    const question_text = screen.getByTestId('questionItem-text');
    expect(question_text.innerHTML).toBe(fakeData.text);
    const question_date = screen.getByTestId('questionItem-date');
    expect(question_date.innerHTML).toBe(formattedDate);
  });

  test('edit question', async () => {
    jest.spyOn(global, 'fetch').mockImplementation((url, options) => {
      expect(url).toBe(`${url_Q}/595`);
      expect(options?.method).toBe('PUT');
      expect(options?.body).toBe(JSON.stringify({ text: 'Test question' }));
      return Promise.resolve(fakeResponse);
    });

    const question_text = screen.getByTestId('questionItem-text');
    expect(question_text.innerHTML).toBe(fakeData.text);
  });

  test('delete question', async () => {
    jest.spyOn(global, 'fetch').mockImplementation((url, options) => {
      expect(url).toBe(`${url_Q}/1`);
      expect(options?.method).toBe('DELETE');

      const fakeResponse = new Response('', {
        status: 204,
      });

      return Promise.resolve(fakeResponse);
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
          isDisable={false}
          editQuestion={editQuestion}
          btnType="save"
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
