import { useState, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import CheckedSubcategory from '../../../../assets/svg/CategoriesChecked.svg';
import UncheckedSubcategory from '../../../../assets/svg/CategoriesUnchecked.svg';
import { addFilterParams, removeFilterParams, resetFilterParams } from '../../../../redux/Slices/AllLearningPathFilterParamsSlice';
import styles from './CheckedContent.module.scss';

const CheckedContent = ({ id, checkedContent, filterFeature }:
  {id:string, checkedContent:string, filterFeature:string}) => {
  const [isChecked, setIsChecked] = useState(false);
  const { checkedContentClass } = styles;
  const dispatch = useDispatch();

  const handleCheck = () => {
    if (isChecked) {
      dispatch(removeFilterParams({ [filterFeature]: id }));
    } else dispatch(addFilterParams({ [filterFeature]: id }));
    setIsChecked((prevState) => !prevState);
  };

  useEffect(() => () => {
    dispatch(resetFilterParams());
  }, []);

  return (
    <div className={checkedContentClass}>
      <img onClick={handleCheck} src={isChecked ? CheckedSubcategory : UncheckedSubcategory} alt="logo" />
      <p>{checkedContent}</p>
    </div>
  );
};
export default CheckedContent;
