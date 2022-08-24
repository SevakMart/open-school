import styles from '../Button-Styles.module.scss';

export const SignUpButton = ({ children, className, onClick }:any) => {
  const styleNames = className.map((className:any) => styles[`${className}`]);
  return (
    <button type="button" className={styleNames.join(' ')} onClick={() => onClick()}>{children}</button>
  );
};
