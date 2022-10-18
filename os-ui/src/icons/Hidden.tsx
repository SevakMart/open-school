import { AiOutlineEyeInvisible } from 'react-icons/ai';

const HiddenIcon = ({ makeVisible }:{makeVisible():void}) => (
  <i><AiOutlineEyeInvisible onClick={() => makeVisible()} /></i>
);
export default HiddenIcon;
