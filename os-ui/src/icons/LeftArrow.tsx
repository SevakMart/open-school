import { BsArrowLeftCircleFill } from 'react-icons/bs';

const LeftArrowIcon = ({ handleArrowClick, testId }:{handleArrowClick():void, testId:string}) => (
  <i data-testid={testId}>
    <BsArrowLeftCircleFill onClick={() => handleArrowClick()} />
  </i>
);
export default LeftArrowIcon;
