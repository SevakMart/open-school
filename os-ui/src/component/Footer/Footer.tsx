import styles from './Footer.module.scss';
import Button from '../Button/Button';

const Footer = () => {
  const {
    mainContainer, categories, becomeMentor, findUsOn,
  } = styles;
  return (
    <div className={mainContainer}>
      <Button>Open-School</Button>
      <div className={categories}>
        <h2>Categories</h2>
        <p>Software Engineering</p>
        <p>Design</p>
        <p>Management</p>
      </div>
      <div className={becomeMentor}>
        <h2>Become a Mentor</h2>
        <p>About</p>
        <p>Help</p>
      </div>
      <div className={findUsOn}>
        <h2>Find Us On</h2>
        <p>Facebook</p>
        <p>Instagram</p>
      </div>
    </div>
  );
};
export default Footer;
