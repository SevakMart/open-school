import React from 'react';
import { AiOutlineEyeInvisible } from 'react-icons/ai';

const styles = {
  position: 'absolute',
  right: '3%',
  marginTop: '9%',
  cursor: 'pointer',
} as React.CSSProperties;

const HiddenIcon = ({ makeVisible }:{makeVisible():void}) => (
  <i style={styles}><AiOutlineEyeInvisible onClick={() => makeVisible()} /></i>
);
export default HiddenIcon;
