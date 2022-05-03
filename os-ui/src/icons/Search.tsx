import React from 'react';
import { BsSearch } from 'react-icons/bs';

const styles = {
  position: 'absolute',
  left: '7%',
  zIndex: '1',
  marginTop: '1.5%',
  cursor: 'pointer',
  color: '#848A9D',
} as React.CSSProperties;

const SearchIcon = () => (
  <BsSearch style={styles} />
);
export default SearchIcon;
