import React, { useRef } from 'react';
import styles from './Search.module.scss';
import SearchIcon from '../../icons/Search';
import { SearchProps } from '../../types/SearchType';

const Search = ({ changeUrlQueries }:SearchProps) => {
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
        placeholder="Search name"
        onKeyPress={handleSearch}
      />
    </div>
  );
};
export default Search;
