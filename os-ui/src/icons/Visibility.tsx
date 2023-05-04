import { AiOutlineEye } from 'react-icons/ai';

const VisibileIcon = ({ makeInvisible }: { makeInvisible: () => void }) => (
  <i style={{
	  position: 'absolute',
	  right: '15px',
	  top: 0,
	  bottom: 0,
	  margin: 'auto',
	  height: '1rem',
	  }}
  >
    <AiOutlineEye onClick={() => makeInvisible()} />
  </i>
);

export default VisibileIcon;
