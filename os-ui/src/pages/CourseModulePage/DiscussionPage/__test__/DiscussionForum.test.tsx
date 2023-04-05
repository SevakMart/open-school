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
  test('check if tag\'s inner are correct', () => {
    const store = createStore(rootReducer);
    render(
      <Provider store={store}>
        <DiscussionForum userInfo={userInfo} />
      </Provider>,
    );
    const forumHeadertitle = screen.getByText(/Discussion Forum/i);
    const MenuListItem_1 = screen.getByText(/Ask Peeps/i);
    const MenuListItem_2 = screen.getByText(/Ask Mentor/i);
    const btn = screen.getByRole('button');
    expect(forumHeadertitle).toBeInTheDocument;
    expect(MenuListItem_1).toBeInTheDocument;
    expect(MenuListItem_2).toBeInTheDocument;
    expect(btn).toBeInTheDocument;
  });

  test('By clicking on the button open or close popUp', () => {
    const store = createStore(rootReducer);
    render(
      <Provider store={store}>
        <DiscussionForum userInfo={userInfo} />
      </Provider>,
    );
    const btn = screen.getByTestId('toggle_btn');
    expect(screen.queryByTestId('askQuestionPopup')).toBeNull();
    userEvent.click(btn);
    expect(screen.queryByTestId('askQuestionPopup')).toBeInTheDocument;
    userEvent.click(btn);
    expect(screen.queryByTestId('askQuestionPopup')).toBeNull();
  });
});
