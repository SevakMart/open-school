import React from 'react';
import { BsSearch } from 'react-icons/bs';

const styles = {
  position: 'absolute',
  zIndex: '1',
  color: '#848A9D',
} as React.CSSProperties;

const SearchIcon = ({ leftPosition, rightPosition }:
  {leftPosition:string|undefined, rightPosition:string|undefined}) => (
    <div style={{ ...styles, left: `${leftPosition}`, right: `${rightPosition}` }}>
      <BsSearch />
    </div>
);
export default SearchIcon;
