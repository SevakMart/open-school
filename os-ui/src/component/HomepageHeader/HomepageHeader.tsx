import Navbar from '../Navbar/Navbar';
import styles from './HomepageHeader.module.scss';
import {
  FREE_EDUCATIONAL_PLATFORM, SIGN_UP, HEADER_INTRODUCTION, SIGN_IN, EDUCATION_PLATFORM_IMAGE,
} from '../../constants/Strings';
import Button from '../Button/Button';

const HomepageHeader = () => {
  const {
    headerContainer, mainContent, leftContent, rightContent, buttonContainer,
  } = styles;
  return (
    <div className={headerContainer}>
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
          <img src={EDUCATION_PLATFORM_IMAGE} alt="Education platform logo" />
        </div>
      </div>
    </div>
  );
};
export default HomepageHeader;
