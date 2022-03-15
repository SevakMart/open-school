import Header from '../../component/Header/Header';
import Footer from '../../component/Footer/Footer';
import Button from '../../component/Button/Button';
import styles from './Homepage.module.scss';

const Homepage = () => {
  const { mainContainer, buttonContainer } = styles;
  return (
    <>
      <Header />
      <div className={mainContainer}>
        <h2>Start Your Journey Now!</h2>
        <div className={buttonContainer}>
          <Button>Sign up as a Student</Button>
          <Button>sign up as a mentor</Button>
        </div>
      </div>
      <Footer />
    </>
  );
};
export default Homepage;
