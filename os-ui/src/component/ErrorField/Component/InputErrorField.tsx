import styles from '../ErrorField-Styles.module.scss';

export const InputErrorField = ({ children, className }:any) => {
  const styleNames = className.map((className:any) => styles[`${className}`]);
  return (
    <h4 className={styleNames}>{children}</h4>
  );
};
