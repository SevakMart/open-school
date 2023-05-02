import { SiGmail } from 'react-icons/si';
import { useLocation } from 'react-router-dom';
import styles from './EmailIcon.module.scss';

const EmailIcon = ({ title }: { title: string }) => {
  const { tooltip, tooltiptext } = styles;
  const location = useLocation();

  if (location && location.pathname && location.pathname.match(/^\/userCourse\/\d+$/)) {
    return (
      <div className={tooltip}>
        <SiGmail style={{ color: '#848A9D', width: '100%', height: '100%' }} />
        <span className={tooltiptext}>{title}</span>
      </div>
    );
  }
  return <SiGmail style={{ color: '#848A9D', width: '100%', height: '100%' }} />;
};

export default EmailIcon;
