import React from 'react';
import styles from '../Input-Styles.module.scss';
/* eslint-disable max-len */
export const CheckedInput = ({
  className, isChecked, onChange, subcategoryItem,
}:{className:Array<string>, isChecked:boolean, onChange:(e:React.SyntheticEvent)=>void, subcategoryItem:{id:number, title:string}}) => {
  const styleNames = className.map((className:any) => styles[`${className}`]);
  return (
    <div className={styleNames.join(' ')}>
      <input type="checkbox" id={`${subcategoryItem.id}`} onChange={(e) => onChange(e)} checked={isChecked} />
      <label data-testid={subcategoryItem.title} htmlFor={`${subcategoryItem.id}`}>{subcategoryItem.title}</label>
    </div>
  );
};
