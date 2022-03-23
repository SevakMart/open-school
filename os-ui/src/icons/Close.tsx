import { GrFormClose } from 'react-icons/gr';

const styles = {
  backgroundColor: 'transparent',
  color: '#848a9d',
  cursor: 'pointer',
  float: 'inline-end',
  border: 'none',
  marginRight: '3%',
  fontSize: '2rem',
  padding: '0',
} as React.CSSProperties;

const CloseIcon = ({ handleClosing }:{handleClosing():void}) => (
  <div style={styles}>
    <GrFormClose onClick={() => handleClosing()} />
  </div>
);
export default CloseIcon;
