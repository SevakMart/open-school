import React from 'react';
import { useTranslation } from 'react-i18next';
import { Input } from '../../../../../component/Input/Input';
import styles from './DiscussionForumSearchBox.module.scss';

type DiscussionForumSearchBoxProps = {
  setSearchTitleInput: (value: string) => void;
  searchTitleInput: string;
};

const DiscussionForumSearchBox = ({ setSearchTitleInput, searchTitleInput }:DiscussionForumSearchBoxProps) => {
  const { t } = useTranslation();
  const { searchbox } = styles;

  return (
    <div className={searchbox}>
      <Input.SearchInput
        className={['search', 'search__allLearningPathSearch']}
        changeUrlQueries={setSearchTitleInput}
        placeholder={t('Search by title')}
        urlQueries={searchTitleInput}
      />
    </div>
  );
};

export default DiscussionForumSearchBox;
