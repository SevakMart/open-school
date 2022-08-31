import React, { useRef } from 'react';
import { useTranslation } from 'react-i18next';
import styles from '../Input-Styles.module.scss';
import SearchIcon from '../../../icons/Search';
import { SearchProps } from '../../../types/SearchType';

export const SearchInput = ({ changeUrlQueries, className }:SearchProps) => {
  const { t } = useTranslation();
  const styleNames = className.map((className:any) => styles[`${className}`]);
  const inputRef = useRef<HTMLInputElement>(null);
  const handleSearch = (e:React.KeyboardEvent) => {
    if (e.key === 'Enter' && inputRef.current && inputRef.current.value) {
      return changeUrlQueries(inputRef.current.value);
    } if (e.key === 'Enter' && inputRef.current && inputRef.current.value === '') {
      return changeUrlQueries('');
    }
  };
  return (
    <div className={styleNames.join(' ')}>
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
