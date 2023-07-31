import { useTranslation } from 'react-i18next';
import { Input } from '../../../../component/Input/Input';
import styles from './Header.module.scss';

const Header = ({ changeUrlQueries }:{changeUrlQueries:(title:string)=>void}) => {
  const { t } = useTranslation();
  const { mainContainer } = styles;
  return (
    <div className={mainContainer}>
      <h1>{t('string.homePage.header.chooseCategories')}</h1>
      <Input.SearchInput
        changeUrlQueries={(title:string) => changeUrlQueries(title)}
        className={['search', 'search__subcategoriesSearch']}
        placeholder={t('Search by name')}
      />
    </div>
  );
};
export default Header;
