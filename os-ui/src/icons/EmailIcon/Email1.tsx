import { SiGmail } from 'react-icons/si';
import { useLocation } from 'react-router-dom';
import styles from './EmailIcon.module.scss';

const EmailIcon = ({ title }: { title: string }) => {
  const { tooltip, tooltiptext, icon } = styles;
  const location = useLocation();

  if (location?.pathname?.match(/^\/userCourse\/\d+$/)) {
    return (
      <div className={tooltip}>
        <SiGmail className={icon} />
        <span className={tooltiptext}>{title}</span>
      </div>
    );
  }
  return <SiGmail className={icon} />;
};

export default EmailIcon;
