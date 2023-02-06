import NavbarOnSignIn from '../../../../component/Navbar-Component/NavbarOnSignIn/NavbarOnSignIn';
import styles from './Header.module.scss';

const Header = () => {
  const { mainContainer } = styles;
  return (
    <div className={mainContainer}>
      <NavbarOnSignIn />
    </div>
  );
};
export default Header;
