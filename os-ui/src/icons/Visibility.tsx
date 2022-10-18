import { AiOutlineEye } from 'react-icons/ai';

const VisibileIcon = ({ makeInvisible }:{makeInvisible():void}) => (
  <i><AiOutlineEye onClick={() => makeInvisible()} /></i>
);
export default VisibileIcon;
