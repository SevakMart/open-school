import styles from './CategoryProfile.module.scss';
import CategoryIcon from '../../assets/svg/CategoryIcon.svg';
import RightArrow from '../../assets/svg/RightArrow.svg';
import { CategoryType } from '../../types/CategoryType';

const CategoryCard = ({ title }:CategoryType) => {
  const {
    mainContainer, categoryInfoContainer, categoryTitle, numberOfSubcategories,
  } = styles;
  return (
    <div className={mainContainer}>
      <img src={CategoryIcon} alt={title} />
      <div className={categoryInfoContainer}>
        <div>
          <p className={categoryTitle} data-testid={title}>{title}</p>
          <p className={numberOfSubcategories}>12 subs</p>
        </div>
        <img src={RightArrow} alt={title} />
      </div>
    </div>
  );
};
export default CategoryCard;
