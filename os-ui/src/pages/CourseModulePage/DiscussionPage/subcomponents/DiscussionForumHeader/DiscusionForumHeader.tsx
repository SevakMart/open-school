import React from 'react';
import { Link } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { onOpen } from '../../../../../redux/Slices/QuestionActionsSlice';
import styles from './DiscussionForumHeader.module.scss';

type DiscussionForumHeaderProps = {
  isBtnClicked: boolean;
  onChangeSection: (value: boolean) => void;
  courseId: string | undefined;
  isLoading: boolean;
};

const DiscussionForumHeader = ({
  isBtnClicked, onChangeSection, courseId, isLoading,
}: DiscussionForumHeaderProps) => {
  const dispatch = useDispatch();
  const { t } = useTranslation();

  const {
    forum_header: forumHeader,
    'forum_header-title': forumHeaderTitle,
    'forum_header-inner': forumHeaderInner,
    'forum_header-menu': forumHeaderMenu,
    'forum_header-list': forumHeaderList,
    'forum_header-list_link': forumHeaderListLink,
    'forum_header-list_link_active': forumHeaderListLinkActive,
    'forum_header-list_underLine': forumHeaderListUnderline,
    'forum_header-list_underLine_active': forumHeaderListUnderlineActive,
    btn,
  } = styles;

  return (
    <div className={forumHeader}>
      <h1 className={forumHeaderTitle}>{t('Discussion Forum')}</h1>
      <div className={forumHeaderInner}>
        <ul className={forumHeaderMenu}>
          <button
            disabled={isLoading}
            type="button"
            className={forumHeaderList}
            onClick={() => onChangeSection(true)}
          >
            <Link
              to={`/userCourse/modulOverview/${courseId}/discussionForum/AskPeers`}
              className={`${forumHeaderListLink} ${
                isBtnClicked ? forumHeaderListLinkActive : ''
              }`}
            >
              {t('Ask Peers')}
            </Link>
            <div
              className={`${forumHeaderListUnderline} ${
                isBtnClicked ? forumHeaderListUnderlineActive : ''
              }`}
            />
          </button>
          <button
            disabled={isLoading}
            type="button"
            className={forumHeaderList}
            onClick={() => onChangeSection(false)}
          >
            <Link
              to={`/userCourse/modulOverview/${courseId}/discussionForum/AskMentor`}
              className={`${forumHeaderListLink} ${
                isBtnClicked ? '' : forumHeaderListLinkActive
              }`}
            >
              {t('Ask Mentor')}
            </Link>
            <div
              className={`${forumHeaderListUnderline} ${
                isBtnClicked ? '' : forumHeaderListUnderlineActive
              }`}
            />
          </button>
        </ul>
        <button type="button" onClick={() => dispatch(onOpen())} className={btn}>
          {t('ASK QUESTION')}
        </button>
      </div>
    </div>
  );
};

export default DiscussionForumHeader;
