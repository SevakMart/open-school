import CourseSummaryEllipseIcon from '../../../../../../assets/svg/Course-Summary-Ellipse.svg';
import styles from './CourseSummaryItem.module.scss';

const CourseSummaryItem = ({ title, value }:{title:string, value:number|string}) => {
  const {
    mainContainer, mainContent, ellipseIcon,
  } = styles;

  const formattedTitle = title === 'CourseLevel'
    ? 'Course Level'
    : title.replace(/([A-Z])/g, ' $1').trim();

  return (
    <div className={mainContainer}>
      <img src={CourseSummaryEllipseIcon} className={ellipseIcon} alt="Ellipse" />
      <div className={mainContent}>
        <h2>{formattedTitle}</h2>
        <p>{value}</p>
      </div>
    </div>
  );
};

export default CourseSummaryItem;
