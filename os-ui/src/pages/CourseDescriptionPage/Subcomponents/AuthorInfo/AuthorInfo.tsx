import { useTranslation } from 'react-i18next';
import LinkedinIcon from '../../../../assets/svg/LinkedIn.svg';
import styles from './AuthorInfo.module.scss';
/* eslint-disable max-len */
const AuthorInfo = ({ mentorName, mentorSurname, mentorLinkedIn }:{mentorName:string, mentorSurname:string, mentorLinkedIn:string}) => {
  const { t } = useTranslation();
  const { authorInformationContainer, authorInfo, mentorProfileImage } = styles;
  return (
    <div className={authorInformationContainer}>
      <h2>{t('string.courseDescriptionPage.title.authorInfo')}</h2>
      <div className={authorInfo}>
        <img className={mentorProfileImage} src="https://reactjs.org/logo-og.png" alt={`${mentorName} ${mentorSurname}`} />
        <p>{`${mentorName} ${mentorSurname}`}</p>
        <a href={mentorLinkedIn}><img src={LinkedinIcon} alt="LinkedIn icon" /></a>
      </div>
    </div>
  );
};
export default AuthorInfo;
