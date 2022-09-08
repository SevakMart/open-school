import styles from '../ErrorField-Styles.module.scss';

export const MainErrorField = ({ children, className }:{children:string, className:string[]}) => {
  const styleNames = className.map((className:any) => styles[`${className}`]);
  return (
    <h2 className={styleNames.join(' ')}>{children}</h2>
  );
};
