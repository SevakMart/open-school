import arrowIcon from '../assets/svg/arrowIcon.svg';

const RightArrowIcon = ({ handleArrowClick, testId }:{handleArrowClick():void, testId:string}) => (
  <i data-testid={testId}>
    <img src={arrowIcon} style={{ margin: '0' }} alt="arrow" onClick={() => handleArrowClick()} />
  </i>
);
export default RightArrowIcon;
