import styles from './Title.module.scss';

const Title = ({ mainTitle, subTitle }:{mainTitle:string, subTitle:string}) => {
  const { main } = styles;

  return (
    <div className={main}>
      <h3>{mainTitle}</h3>
      <h2>{subTitle}</h2>
    </div>
  );
};
export default Title;
