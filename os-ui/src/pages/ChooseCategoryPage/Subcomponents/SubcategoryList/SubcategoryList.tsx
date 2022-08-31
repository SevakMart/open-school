import { Input } from '../../../../component/Input/Input';
import { params, addSearchQueries, deleteSpecificSearchQuery } from '../../../../services/queryParams/queryParams';
import styles from './SubcategoryList.module.scss';

const SubcategoryList = ({ subcategories }:{subcategories:Array<{id:number, title:string}>}) => {
  const { subMainContent } = styles;

  const handleChange = (e:React.SyntheticEvent) => {
    // addSearchQueries();
  };

  return (
    <div className={subMainContent}>
      {subcategories.length > 0 && subcategories.map((subcategory) => (
        <Input.CheckedInput
          key={+subcategory.id}
          subcategoryItem={subcategory}
          className={['subcategoryContent']}
          isChecked={false}
          onChange={handleChange}
        />
        /* <div className={subcategoryContent} key={+subcategory.id}>
          {
                  (subcategoryIdArray as Array<number>).some((id) => id === +subcategory.id)
                    ? <input type="checkbox" id={`${subcategory.id}`} onChange={handleChange} checked />
                    : <input type="checkbox" id={`${subcategory.id}`} onChange={handleChange} checked={false} />
                }
          <label data-testid={subcategory.title} htmlFor={`${subcategory.id}`}>{subcategory.title}</label>
        </div> */
      ))}
    </div>
  );
};
export default SubcategoryList;
