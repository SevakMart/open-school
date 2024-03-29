import CheckedSubcategory from '../../../../assets/svg/CategoriesChecked.svg';
import UncheckedSubcategory from '../../../../assets/svg/CategoriesUnchecked.svg';
import { useCheck } from '../../../../custom-hooks/useCheck';
import styles from './SubcategoryContent.module.scss';

const SubcategoryContent = ({ subcategoryItem }:{subcategoryItem:{id:number, title:string}}) => {
  const [isChecked, handleChecking] = useCheck(subcategoryItem.title, subcategoryItem.id);
  const { mainContent, checkboxContent } = styles;

  return (
    <div className={mainContent}>
      <div onClick={() => handleChecking()} className={checkboxContent}>
        <img src={isChecked ? CheckedSubcategory : UncheckedSubcategory} alt="logo" />
      </div>
      <p>{subcategoryItem.title}</p>
    </div>
  );
};
export default SubcategoryContent;
