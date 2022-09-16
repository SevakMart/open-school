import { useCheck } from '../../../../custom-hooks/useCheck';
import CheckedSubcategory from '../../../../assets/svg/CategoriesChecked.svg';
import UncheckedSubcategory from '../../../../assets/svg/CategoriesUnchecked.svg';
import { addFilterParams, removeFilterParams } from '../../../../redux/Slices/AllLearningPathFilterParamsSlice';
import styles from './CheckedContent.module.scss';

/* eslint-disable max-len */

const CheckedContent = ({ id, checkedContent, filterFeature }:
  {id:string, checkedContent:string, filterFeature:string}) => {
  const [isChecked, handleChecking, dispatch] = useCheck(checkedContent, id);
  const { checkedContentClass } = styles;

  const handleCheck = () => {
    /* The state change will occur after dispatching the action so the the dispatched actions should be reversed */
    if (isChecked) {
      dispatch(removeFilterParams({ [filterFeature]: id }));
    } else dispatch(addFilterParams({ [filterFeature]: id }));
    handleChecking();
  };

  return (
    <div className={checkedContentClass}>
      <img onClick={handleCheck} src={isChecked ? CheckedSubcategory : UncheckedSubcategory} alt="logo" />
      <p>{checkedContent}</p>
    </div>
  );
};
export default CheckedContent;
