import arrowIcon from '../assets/svg/arrowIcon.svg';

const LeftArrowIcon = ({ handleArrowClick, testId }:{handleArrowClick():void, testId:string}) => (
  <i data-testid={testId}>
    <img src={arrowIcon} style={{ transform: 'rotate(180deg)' }} alt="arrow" onClick={() => handleArrowClick()} />
  </i>
);
export default LeftArrowIcon;
