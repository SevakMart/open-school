import { AiOutlineEyeInvisible } from 'react-icons/ai';

const HiddenIcon = ({ makeVisible }: { makeVisible(): void }) => (
  <i style={{
    position: 'absolute',
    right: '15px',
    top: 0,
    bottom: 0,
    margin: 'auto',
    height: '1rem',
  }}
  >
    <AiOutlineEyeInvisible onClick={() => makeVisible()} />
  </i>
);

export default HiddenIcon;
