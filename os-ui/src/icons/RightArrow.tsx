import { BsArrowRightCircleFill } from 'react-icons/bs';

const RightArrowIcon = ({ handleArrowClick, testId }:{handleArrowClick():void, testId:string}) => (
  <i data-testid={testId}>
    <BsArrowRightCircleFill onClick={() => handleArrowClick()} />
  </i>
);
export default RightArrowIcon;
