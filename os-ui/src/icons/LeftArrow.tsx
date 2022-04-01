import React from 'react';
import { BsArrowLeftCircleFill } from 'react-icons/bs';

const styles = {
  position: 'absolute',
  zIndex: '1',
  fontSize: '3rem',
  cursor: 'pointer',
} as React.CSSProperties;

const LeftArrowIcon = ({ handleArrowClick, testId }:{handleArrowClick():void, testId:string}) => (
  <i style={styles} data-testid={testId}>
    <BsArrowLeftCircleFill onClick={() => handleArrowClick()} />
  </i>
);
export default LeftArrowIcon;
