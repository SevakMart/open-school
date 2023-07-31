import { useTranslation } from 'react-i18next';
import styles from './CategoryProfile.module.scss';
import CategoryIcon from '../../assets/svg/CategoryIcon.svg';
import RightArrow from '../../assets/svg/RightArrow.svg';
import { CategoryType } from '../../types/CategoryType';

const CategoryCard = ({ category }:{category:CategoryType}) => {
  const {
    mainContainer, categoryInfoContainer, categoryBox, mainContainerRightArrow, categoryTitle, numberOfSubcategories,
  } = styles;
  const { t } = useTranslation();
  return (
    <div className={mainContainer}>
      <img src={CategoryIcon} alt={category.title} />
      <div className={categoryInfoContainer}>
        <div className={categoryBox}>
          <p className={categoryTitle} data-testid={category.title}>{category.title}</p>
          <p className={numberOfSubcategories}>{t('12 Subcategories')}</p>
        </div>
        <img className={mainContainerRightArrow} src={RightArrow} alt={category.title} />
      </div>
    </div>
  );
};
export default CategoryCard;
