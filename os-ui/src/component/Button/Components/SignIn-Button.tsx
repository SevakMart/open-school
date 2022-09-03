import styles from '../Button-Styles.module.scss';

/* eslint-disable max-len */

export const SignInButton = ({ children, className, onClick }:{children:string, className:Array<string>, onClick:()=>void}) => {
  const styleNames = className.map((className:string) => styles[`${className}`]);
  return (
    <button type="button" className={styleNames.join(' ')} onClick={() => onClick()}>{children}</button>
  );
};
