import { useLocation, useNavigate } from 'react-router-dom';
import userService from '../../../services/userService';
import styles from '../Button-Styles.module.scss';

/* eslint-disable max-len */

export const NextButton = ({
  children, className, userInfoId, userInfoToken,
}:{children:React.Component, className:Array<string>, userInfoId:number, userInfoToken:string}) => {
  const styleNames = className.map((className:any) => styles[`${className}`]);
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const navigate = useNavigate();

  const handleNextClick = () => {
    const subcategoryIdArray = [];
    for (const values of params.values()) {
      subcategoryIdArray.push(+values);
    }
    userService.savePreferredCategories(userInfoId, userInfoToken, subcategoryIdArray)
      .then(() => navigate('/myLearningPath'));
  };

  return (
    <button type="button" className={styleNames.join(' ')} onClick={handleNextClick}>{children}</button>
  );
};
