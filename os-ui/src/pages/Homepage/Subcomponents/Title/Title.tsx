import styles from './Title.module.scss';
/* eslint-disable-next-line max-len */
const Title = ({ mainTitle, subTitle, isMentor }:{mainTitle:string, subTitle:string, isMentor:boolean}) => {
  const { main } = styles;
  const paddingTop = isMentor ? '5%' : '5%';
  return (
    <div className={main} style={{ paddingTop }}>
      <h3>{mainTitle}</h3>
      <h2>{subTitle}</h2>
    </div>
  );
};
export default Title;
