import React, { useRef } from 'react';
import { useTranslation } from 'react-i18next';
import styles from './Search.module.scss';
import SearchIcon from '../../icons/Search';
import { SearchProps } from '../../types/SearchType';

const Search = ({ changeUrlQueries }:SearchProps) => {
  const { t } = useTranslation();
  const { inputContent } = styles;
  const inputRef = useRef<HTMLInputElement>(null);
  const handleSearch = (e:React.KeyboardEvent) => {
    if (e.key === 'Enter' && inputRef.current && inputRef.current.value) {
      return changeUrlQueries(inputRef.current.value);
    } if (e.key === 'Enter' && inputRef.current && inputRef.current.value === '') {
      return changeUrlQueries('');
    }
  };
  return (
    <div className={inputContent}>
      <SearchIcon />
      <input
        ref={inputRef}
        type="text"
        placeholder={t('string.search')}
        data-testid="searchInput"
        onKeyPress={handleSearch}
      />
    </div>
  );
};
export default Search;
