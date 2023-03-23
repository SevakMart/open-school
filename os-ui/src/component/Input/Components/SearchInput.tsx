import React, {
  useRef, useEffect, useMemo, useState, createContext,
} from 'react';
import { useTranslation } from 'react-i18next';
import styles from '../Input-Styles.module.scss';
import SearchIcon from '../../../icons/Search';
import { useFocus } from '../../../custom-hooks/useFocus';
import { SearchProps } from '../../../types/SearchType';

interface SearchContextType {
  searchQuery: string;
  setSearchQuery?: (query: string) => void;
}

const SearchContext = createContext<SearchContextType>({
  searchQuery: '',
  setSearchQuery: undefined,
});

export const SearchInput = ({ changeUrlQueries, className }: SearchProps) => {
  const { t } = useTranslation();
  const styleNames = className.map((className: string) => styles[className]);
  const inputRef = useRef<HTMLInputElement | null>(null);
  const searchContainerRef = useRef<HTMLDivElement | null>(null);
  const [handleOnFocus, handleOnBlur] = useFocus('white', searchContainerRef.current);

  const [searchQuery, setSearchQuery] = useState('');

  const handleSearch = () => {
    if (inputRef.current && inputRef.current.value) {
      setSearchQuery(inputRef.current.value);
      changeUrlQueries(inputRef.current.value);
    } else {
      setSearchQuery('');
      changeUrlQueries('');
    }
  };

  useEffect(() => {
    if (inputRef.current) {
      inputRef.current.value = searchQuery;
      changeUrlQueries(searchQuery);
    }
  }, [searchQuery, changeUrlQueries]);

  const memoizedValue = useMemo(() => ({ searchQuery, setSearchQuery: (query: string) => setSearchQuery(query) }), [searchQuery, setSearchQuery]);

  return (
    <SearchContext.Provider value={memoizedValue}>
      <div className={styleNames.join(' ')} ref={searchContainerRef}>
        <SearchIcon />
        <input
          ref={inputRef}
          type="text"
          placeholder={t('string.search')}
          data-testid="searchInput"
          onChange={handleSearch}
          onFocus={() => handleOnFocus()}
          onBlur={() => handleOnBlur()}
        />
      </div>
    </SearchContext.Provider>
  );
};
