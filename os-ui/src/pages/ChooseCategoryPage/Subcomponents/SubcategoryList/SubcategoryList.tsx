import SubcategoryContent from '../SubcategoryContent/SubcategoryContent';
import styles from './SubcategoryList.module.scss';

const SubcategoryList = ({ subcategories }:{subcategories:Array<{id:number, title:string}>}) => {
  const { subMainContent } = styles;

  return (
    <div className={subMainContent}>
      {subcategories.length > 0 && subcategories.map((subcategory) => (
        <SubcategoryContent
          key={+subcategory.id}
          subcategoryItem={subcategory}
        />
      ))}
    </div>
  );
};
export default SubcategoryList;
/* <Input.CheckedInput
          key={+subcategory.id}
          subcategoryItem={subcategory}
          className={['subcategoryContent']}
          isChecked={false}
          onChange={handleChange}
      /> */
