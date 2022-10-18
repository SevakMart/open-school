import CourseSummaryEllipseIcon from '../../../../../../assets/svg/Course-Summary-Ellipse.svg';
import styles from './CourseSummaryItem.module.scss';

const CourseSummaryItem = ({ title, value }:{title:string, value:number|string}) => {
  const {
    mainContainer, mainContent,
  } = styles;
  return (
    <div className={mainContainer}>
      <img src={CourseSummaryEllipseIcon} alt="Ellipse" />
      <div className={mainContent}>
        <h2>{title}</h2>
        <p>{value}</p>
      </div>
    </div>
  );
};
export default CourseSummaryItem;
