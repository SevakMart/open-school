import { AiOutlineLinkedin } from 'react-icons/ai';
import styles from './LinkedInIcon.module.scss';

const LinkedinIcon = ({ href }: { href: string }) => {
  const { icon } = styles;
  return (
    <a href={href} target="_blank" rel="noopener noreferrer">
      <AiOutlineLinkedin className={icon} />
    </a>
  );
};

export default LinkedinIcon;
