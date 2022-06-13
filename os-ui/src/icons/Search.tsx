import React from 'react';
import { BsSearch } from 'react-icons/bs';

const styles = {
  zIndex: '1',
  color: '#848A9D',
} as React.CSSProperties;

const SearchIcon = () => (
  <div style={styles}>
    <BsSearch />
  </div>
);
export default SearchIcon;
