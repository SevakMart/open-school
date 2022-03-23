import React from 'react';
import { AiOutlineEye } from 'react-icons/ai';

const styles = {
  position: 'absolute',
  right: '3%',
  marginTop: '9%',
  cursor: 'pointer',
} as React.CSSProperties;

const VisibileIcon = ({ makeInvisible }:{makeInvisible():void}) => (
  <i style={styles}><AiOutlineEye onClick={() => makeInvisible()} /></i>
);
export default VisibileIcon;
