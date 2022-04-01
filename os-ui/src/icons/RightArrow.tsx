import React from 'react';
import { BsArrowRightCircleFill } from 'react-icons/bs';

const styles = {
  position: 'absolute',
  zIndex: '1',
  fontSize: '3rem',
  right: '6%',
  cursor: 'pointer',
} as React.CSSProperties;

const RightArrowIcon = ({ handleArrowClick, testId }:{handleArrowClick():void, testId:string}) => (
  <i style={styles} data-testid={testId}>
    <BsArrowRightCircleFill onClick={() => handleArrowClick()} />
  </i>
);
export default RightArrowIcon;
