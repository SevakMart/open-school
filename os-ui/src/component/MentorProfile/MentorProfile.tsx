import { MentorType } from '../../types/MentorType';
import EmailIcon from '../../icons/Email';
import LinkedinIcon from '../../icons/Linkedin';
import BookmarkIcon from '../../icons/Bookmark';
import BookIcon from '../../icons/Book';
import styles from './MentorProfile.module.scss';

const MentorCard = ({
  name, surname, professionName, companyName, courseCount, userImgPath, emailPath, linkedinPath,
}:MentorType) => {
  const {
    mainContainer, headerContainer, MailLinkedinIconsContainer, bodyContainer, mentorInfo,
    mentorAvatar, mentorInfoContainer, mentorExtraInfo,
  } = styles;
  return (
    <div className={mainContainer}>
      <div className={headerContainer}>
        <div className={MailLinkedinIconsContainer}>
          <p onClick={() => { window.location.href = emailPath; }}><EmailIcon /></p>
          <p>|</p>
          <p onClick={() => { window.location.href = linkedinPath; }}><LinkedinIcon /></p>
        </div>
        <p><BookmarkIcon iconSize="1rem" /></p>
      </div>
      <div className={bodyContainer}>
        <div className={mentorInfoContainer}>
          <div className={mentorAvatar}>
            <img src={userImgPath} alt="mentor_avatar" />
          </div>
          <div className={mentorInfo}>
            <h3 data-testid={`${name} ${surname}`}>
              {name}
              {' '}
              {surname}
            </h3>
            <p data-testid={professionName}>{professionName}</p>
            <p data-testid={companyName}>{companyName}</p>
          </div>
        </div>
        <div className={mentorExtraInfo}>
          <BookIcon />
          <p data-testid={courseCount}>
            {courseCount}
          </p>
        </div>
      </div>
    </div>
  );
};
export default MentorCard;
