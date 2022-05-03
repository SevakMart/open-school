import React, { useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './Search.module.scss';
import SearchIcon from '../../icons/Search';
import { SearchProps } from '../../types/SearchType';

const Search = ({ pathname, searchKeyName, changeUrlQueries }:SearchProps) => {
  const navigate = useNavigate();
  const { inputContent } = styles;
  const inputRef = useRef<HTMLInputElement>(null);
  const handleSearch = (e:React.KeyboardEvent) => {
    if (e.key === 'Enter' && inputRef.current && inputRef.current.value) {
      navigate(`${pathname}?${searchKeyName}=${inputRef.current.value}`);
      return changeUrlQueries(inputRef.current.value);
    } if (e.key === 'Enter' && inputRef.current && inputRef.current.value === '') {
      navigate(`${pathname}?${searchKeyName}=all`);
      return changeUrlQueries('');
    }
  };
  return (
    <div className={inputContent}>
      <input
        ref={inputRef}
        type="text"
        placeholder="Search name"
        onKeyPress={handleSearch}
      />
      <SearchIcon />
    </div>
  );
};
export default Search;
