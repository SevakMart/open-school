import Navbar from '../Navbar/Navbar';
import Polygon from '../../svg/Polygon.svg';
import Rectangle from '../../svg/Rectangle.svg';
import styles from './Header.module.scss';
import {
  FREE_EDUCATIONAL_PLATFORM, SIGN_UP, HEADER_INTRODUCTION, SIGN_IN,
} from '../../constants/Strings';
import Button from '../Button/Button';

const Header = () => {
  const {
    mainContent, leftContent, rightContent, buttonContainer, arrowImage,
  } = styles;
  return (
    <>
      <Navbar />
      <div className={mainContent}>
        <div className={leftContent}>
          <h1>{FREE_EDUCATIONAL_PLATFORM}</h1>
          <p>{HEADER_INTRODUCTION}</p>
          <div className={buttonContainer}>
            <Button>{SIGN_UP}</Button>
            <Button>{SIGN_IN}</Button>
          </div>
        </div>
        <div className={rightContent}>
          <div className={arrowImage}>
            <img src={Polygon} alt="arrow icon" />
            <img src={Rectangle} alt="arrow icon" />
          </div>

        </div>
      </div>
    </>
  );
};
export default Header;
