import styles from '../Button-Styles.module.scss';

export const SignInButton = ({ children, className, onClick }:any) => {
  const styleNames = className.map((className:any) => styles[`${className}`]);
  return (
    <button type="button" className={styleNames.join(' ')} onClick={() => onClick()}>{children}</button>
  );
};
