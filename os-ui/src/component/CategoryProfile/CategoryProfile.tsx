import styles from './CategoryProfile.module.scss';
import { CategoryType } from '../../types/CategoryType';

const CategoryCard = ({ title, logoPath }:CategoryType) => {
  const { mainContainer, categoryInfoContainer, logoContainer } = styles;
  return (
    <div className={mainContainer}>
      <div className={categoryInfoContainer}>
        <div className={logoContainer}>
          <img src={logoPath} alt={title} />
        </div>
        <p data-testid="categoryTitle">{title}</p>
      </div>
    </div>
  );
};
export default CategoryCard;
