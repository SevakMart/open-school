import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import { CHOOSE_CATEGORIES_HEADER } from '../../constants/Strings';
import styles from './ChooseCategoryPage.module.scss';

const ChooseCategoryPage = () => {
  const { mainHeader } = styles;
  return (
    <>
      <NavbarOnSignIn />
      <h1 className={mainHeader}>{CHOOSE_CATEGORIES_HEADER}</h1>
    </>
  );
};
export default ChooseCategoryPage;
