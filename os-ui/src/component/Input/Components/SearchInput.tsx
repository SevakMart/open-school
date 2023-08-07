import React, {
  useRef, useEffect, useState, useMemo,
} from 'react';
import styles from '../Input-Styles.module.scss';
import SearchIcon from '../../../icons/Search';
import { useFocus } from '../../../custom-hooks/useFocus';
import { SearchProps } from '../../../types/SearchType';
import CloseIcon from '../../../icons/Close';

interface SearchContextType {
  searchQuery: string;
  setSearchQuery?: (query: string) => void;
}

const SearchContext = React.createContext<SearchContextType>({
  searchQuery: '',
  setSearchQuery: undefined,
});

export const SearchInput = ({
  urlQueries = '',
  changeUrlQueries,
  className,
  placeholder,
}: SearchProps) => {
  const styleNames = className.map((className: string) => styles[className]);
  const searchContainerRef = useRef<HTMLDivElement | null>(null);
  const [handleOnFocus, handleOnBlur] = useFocus('white', searchContainerRef.current);
  const [searchQuery, setSearchQuery] = useState(urlQueries);

  const handleDeleteInput = () => {
    setSearchQuery('');
    changeUrlQueries('');
  };

  const handleSearch = (needle: string) => {
    setSearchQuery(needle);
    changeUrlQueries(needle);
  };

  useEffect(() => {
    setSearchQuery(urlQueries);
  }, [urlQueries]);

  const memoizedValue = useMemo(
    () => ({ searchQuery, setSearchQuery }),
    [searchQuery],
  );

  return (
    <SearchContext.Provider value={memoizedValue}>
      <div className={styleNames.join(' ')} ref={searchContainerRef}>
        <SearchIcon />
        <input
          type="text"
          placeholder={placeholder}
          data-testid="searchInput"
          onFocus={() => handleOnFocus()}
          onBlur={() => handleOnBlur()}
          onChange={(e) => handleSearch(e.target.value)}
          value={searchQuery}
        />
        <div className={styles.closeIcon} onClick={handleDeleteInput}>
          <CloseIcon />
        </div>
      </div>
    </SearchContext.Provider>
  );
};
