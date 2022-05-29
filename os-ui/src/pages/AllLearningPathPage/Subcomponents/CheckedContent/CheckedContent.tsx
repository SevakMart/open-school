import styles from './CheckedContent.module.scss';

const CheckedContent = ({ id, checkedContent }:{id:string, checkedContent:string}) => {
  const { checkedContentClass } = styles;
  return (
    <div className={checkedContentClass}>
      <input type="checkbox" name="checkbox" id={id} />
      <label htmlFor={id}>{checkedContent}</label>
    </div>
  );
};
export default CheckedContent;
