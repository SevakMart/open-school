simport React from 'react';
import { BsSearch } from 'react-icons/bs';

const styles = {
  color: '#848A9D',
  marginLeft: '16px',
} as React.CSSProperties;

const SearchIcon = () => (
  <div style={styles}>
    <BsSearch />
  </div>
);
export default SearchIcon;
