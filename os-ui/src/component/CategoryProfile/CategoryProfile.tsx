import styles from './CategoryProfile.module.scss';
import CategoryIcon from '../../assets/svg/CategoryIcon.svg';
import RightArrow from '../../assets/svg/RightArrow.svg';
import { CategoryType } from '../../types/CategoryType';

const CategoryCard = ({ category }:{category:CategoryType}) => {
  const {
    mainContainer, categoryInfoContainer, categoryTitle, numberOfSubcategories,
  } = styles;
  return (
    <div className={mainContainer}>
      <img src={CategoryIcon} alt={category.title} />
      <div className={categoryInfoContainer}>
        <div>
          <p className={categoryTitle} data-testid={category.title}>{category.title}</p>
          <p className={numberOfSubcategories}>{category.subCategoryCount}</p>
        </div>
        <img src={RightArrow} alt={category.title} />
      </div>
    </div>
  );
};
export default CategoryCard;
