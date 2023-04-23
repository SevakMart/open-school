import { createStore } from '@reduxjs/toolkit';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Provider } from 'react-redux';
import rootReducer from '../../../../redux/RootReducer';
import DiscussionForum from '../DiscussionForum';

const userInfo = {
  token: '123',
  id: 1,
};

describe('DiscussionForum', () => {
  const store = createStore(rootReducer);
  render(
    <Provider store={store}>
      <DiscussionForum userInfo={userInfo} />
    </Provider>,
  );

  afterEach(() => {
    jest.clearAllMocks();
  });

  test('check if tag\'s inner are correct', () => {
    const forumHeadertitle = screen.getByText(/Discussion Forum/i);
    const MenuListItemPeers = screen.getByText(/Ask Peers/i);
    const MenuListItemMentor = screen.getByText(/Ask Mentor/i);
    const btn = screen.getByTestId('toggle-btn');
    expect(forumHeadertitle).toBeInTheDocument;
    expect(MenuListItemPeers).toBeInTheDocument;
    expect(MenuListItemMentor).toBeInTheDocument;
    expect(btn).toBeInTheDocument;
  });

  test('By clicking on the button open or close popUp', () => {
    const store = createStore(rootReducer);
    render(
      <Provider store={store}>
        <DiscussionForum userInfo={userInfo} />
      </Provider>,
    );

    const btn = screen.getByTestId('toggle-btn');
    expect(screen.queryByTestId('askQuestionPopup')).toBeNull();
    userEvent.click(btn);
    expect(screen.queryByTestId('askQuestionPopup')).toBeInTheDocument;
    userEvent.click(btn);
    expect(screen.queryByTestId('askQuestionPopup')).toBeNull();
  });
});
