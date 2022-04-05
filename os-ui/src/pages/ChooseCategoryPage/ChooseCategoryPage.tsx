import NavbarOnSignIn from '../../component/NavbarOnSignIn/NavbarOnSignIn';
import Search from '../../component/Search/Search';
import { CHOOSE_CATEGORIES_HEADER } from '../../constants/Strings';
import styles from './ChooseCategoryPage.module.scss';

const ChooseCategoryPage = () => {
  const { mainHeader } = styles;
  return (
    <>
      <NavbarOnSignIn />
      <h1 className={mainHeader}>{CHOOSE_CATEGORIES_HEADER}</h1>
      <Search pathname="/choose_categories" searchKeyName="searchCategories" />
    </>
  );
};
export default ChooseCategoryPage;
