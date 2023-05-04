import { useTranslation } from 'react-i18next';
import styles from './AuthorInfo.module.scss';
import LinkedinIcon1 from '../../../../icons/LinkedinIcon/Linkedin1';
import EmailIcon1 from '../../../../icons/EmailIcon/Email1';

const AuthorInfo = ({ mentorName, mentorSurname, mentorLinkedIn }:{mentorName:string, mentorSurname:string, mentorLinkedIn:string}) => {
  const { t } = useTranslation();
  const {
    authorInformationContainer, authorInfo, mentorProfileImage, icon, iconContent,
  } = styles;

  const handleLinkedinClick = () => {
    window.location.href = 'https://www.linkedin.com/';
  };

  return (
    <div className={authorInformationContainer}>
      <h2>{t('string.courseDescriptionPage.title.authorInfo')}</h2>
      <div className={authorInfo}>
        <img className={mentorProfileImage} src="https://reactjs.org/logo-og.png" alt={`${mentorName} ${mentorSurname}`} />
        <p>{`${mentorName} ${mentorSurname}`}</p>
        <div className={iconContent}>
          <div className={icon} onClick={handleLinkedinClick}><LinkedinIcon1 href={mentorLinkedIn} /></div>
          <div className={icon}><EmailIcon1 title="example@example.com" /></div>
        </div>
      </div>
    </div>
  );
};
export default AuthorInfo;
