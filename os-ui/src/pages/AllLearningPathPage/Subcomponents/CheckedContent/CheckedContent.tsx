import { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate, useLocation } from 'react-router-dom';
import { addFilterParams, removeFilterParams } from '../../../../redux/Slices/AllLearningPathFilterParamsSlice';
import styles from './CheckedContent.module.scss';

const CheckedContent = ({ id, checkedContent, filterFeature }:
  {id:string, checkedContent:string, filterFeature:string}) => {
  const navigate = useNavigate();
  const location = useLocation();
  const dispatch = useDispatch();
  const [isChecked, setIsChecked] = useState(false);
  const params = new URLSearchParams(location.search);
  const { checkedContentClass } = styles;

  const handleChange = () => {
    setIsChecked((prevState) => !prevState);
  };
  useEffect(() => {
    if (params.has(checkedContent)) setIsChecked(true);
    /* dispatch(addFilterParams({ [filterFeature]: id })); */
  }, []);

  useEffect(() => {
    if (isChecked) {
      params.set(checkedContent, id);
      dispatch(addFilterParams({ [filterFeature]: id }));
      navigate(`/exploreLearningPaths?${params}`);
    } else {
      params.delete(checkedContent);
      dispatch(removeFilterParams({ [filterFeature]: id }));
      navigate(`/exploreLearningPaths?${params}`);
    }
  }, [isChecked]);

  return (
    <div className={checkedContentClass}>
      <input type="checkbox" name="checkbox" checked={!!params.has(checkedContent)} onChange={handleChange} id={id} />
      <label htmlFor={id}>{checkedContent}</label>
    </div>
  );
};
export default CheckedContent;
