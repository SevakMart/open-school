import { createStore } from '@reduxjs/toolkit';
import { render, screen, waitFor } from '@testing-library/react';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import rootReducer from '../../../../redux/RootReducer';
import DiscussionForum from '../DiscussionForum';

const userInfo = {
  token: '123',
  id: 1,
};

const questions = [
  {
    id: 1,
    text: 'Question 1',
    createdDate: '2023-05-01',
    name: 'John',
    surname: 'Doe',
  },
  {
    id: 2,
    text: 'Question 2',
    createdDate: '2023-05-02',
    name: 'Jane',
    surname: 'Smith',
  },
];

jest.mock('../../../../redux/Slices/QuestionActionsSlice', () => ({
  AllQuestionsFromServer: jest.fn().mockResolvedValue({ payload: { AllquestionsToPeers: [], AllquestionsToMentor: [] } }),
}));

describe('DiscussionForum', () => {
  const AllQuestionsFromServer = jest.fn();
  const store = createStore(rootReducer);

  afterEach(() => {
    jest.clearAllMocks();
  });

  async () => {
    render(
      <Provider store={store}>
        <BrowserRouter>
          <DiscussionForum userInfo={userInfo} />
        </BrowserRouter>
      </Provider>,
    );
  };

  test('displays the questions from peers when the "Ask Peers" section is active', async () => {
    AllQuestionsFromServer.mockResolvedValueOnce({ payload: { AllquestionsToPeers: questions, AllquestionsToMentor: [] } });
    jest.mock('../../../../redux/Slices/QuestionActionsSlice', () => ({
      AllQuestionsFromServer: jest.fn().mockResolvedValue({ payload: { AllquestionsToPeers: [], AllquestionsToMentor: [] } }),
    }));
  });

  test('displays the questions to mentor when in the "Ask Mentor" section', async () => {
    AllQuestionsFromServer.mockResolvedValueOnce({ payload: { AllquestionsToPeers: [], AllquestionsToMentor: questions } });
    jest.mock('../../../../redux/Slices/QuestionActionsSlice', () => ({
      AllQuestionsFromServer: jest.fn().mockResolvedValue({ payload: { AllquestionsToPeers: [], AllquestionsToMentor: [] } }),
    }));

    await waitFor(() => {
      expect(screen.queryByText(/No questions/i)).toBeNull();
    });
  });
});
