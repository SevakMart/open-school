import styles from './Title.module.scss';
/* eslint-disable-next-line max-len */
const Title = ({ mainTitle, subTitle, isMentor }:{mainTitle:string, subTitle:string, isMentor:boolean}) => {
  const { main } = styles;

  return (
    <div className={main} style={isMentor ? { paddingTop: '5%' } : undefined}>
      <h3>{mainTitle}</h3>
      <h2>{subTitle}</h2>
    </div>
  );
};
export default Title;
