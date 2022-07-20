import styles from './CourseSummaryItem.module.scss';

const CourseSummaryItem = ({ title, value }:{title:string, value:number|string}) => {
  const {
    mainContainer, mainSubContainer, mainContent, dotNotation,
  } = styles;
  return (
    <div className={mainContainer}>
      <div className={mainSubContainer}>
        <div className={dotNotation} />
        <div className={mainContent}>
          <h2>{title}</h2>
          <p>{value}</p>
        </div>
      </div>
    </div>
  );
};
export default CourseSummaryItem;
