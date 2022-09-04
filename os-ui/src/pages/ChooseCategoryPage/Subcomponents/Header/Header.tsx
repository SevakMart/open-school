import { useTranslation } from 'react-i18next';
import { Input } from '../../../../component/Input/Input';
import NavbarOnSignIn from '../../../../component/Navbar-Component/NavbarOnSignIn/NavbarOnSignIn';
import styles from './Header.module.scss';

const Header = ({ changeUrlQueries }:{changeUrlQueries:(title:string)=>void}) => {
  const { t } = useTranslation();
  const { mainContainer } = styles;
  return (
    <div className={mainContainer}>
      <NavbarOnSignIn />
      <h1>{t('string.homePage.header.chooseCategories')}</h1>
      <Input.SearchInput
        changeUrlQueries={(title:string) => changeUrlQueries(title)}
        className={['subcategoriesSearch']}
      />
    </div>
  );
};
export default Header;
