import { MentorType } from '../../types/MentorType';
import EmailIcon from '../../icons/Email';
import LinkedinIcon from '../../icons/Linkedin';
import BookmarkIcon from '../../icons/Bookmark';
import BookIcon from '../../icons/Book';
import Separator from '../../assets/svg/Separator.svg';
import styles from './MentorProfile.module.scss';

/* eslint-disable max-len */

const MentorCard = ({
  mentor, isHomepageNotSignedMentorCard, saveMentor, deleteMentor,
}:
  {mentor:MentorType, isHomepageNotSignedMentorCard:boolean, saveMentor?:(mentorName:string, mentorId:number)=>void,
    deleteMentor?:(mentorName:string, mentorId:number)=>void, }) => {
  const {
    mainContainer, headerContainer, MailLinkedinIconsContainer, bodyContainer, mentorInfo,
    mentorAvatar, mentorInfoContainer, mentorExtraInfo,
  } = styles;
  return (
    <div className={mainContainer}>
      <div className={headerContainer}>
        <div className={MailLinkedinIconsContainer}>
          <p onClick={() => { window.location.href = mentor.emailPath; }}><EmailIcon /></p>
          <img src={Separator} alt="Separator" />
          <p onClick={() => { window.location.href = mentor.linkedinPath; }}><LinkedinIcon /></p>
        </div>
        <p>
          <BookmarkIcon
            iconSize="1rem"
            mentorId={mentor.id}
            mentorName={`${mentor.name} ${mentor.surname}`}
            isHomepageNotSignedInMentor={isHomepageNotSignedMentorCard}
            saveMentor={(mentorName:string, mentorId:number) => saveMentor && saveMentor(mentorName, mentorId)}
            deleteMentor={(mentorName:string, mentorId:number) => deleteMentor && deleteMentor(mentorName, mentorId)}
          />
        </p>
      </div>
      <div className={bodyContainer}>
        <div className={mentorInfoContainer}>
          <div className={mentorAvatar}>
            <img src={mentor.userImgPath} alt="mentor_avatar" />
          </div>
          <div className={mentorInfo}>
            <h3 data-testid={`${mentor.name} ${mentor.surname}`}>
              {mentor.name}
              {' '}
              {mentor.surname}
            </h3>
            <p data-testid={mentor.professionName}>{mentor.professionName}</p>
            <p data-testid={mentor.companyName}>{mentor.companyName}</p>
          </div>
        </div>
        <div className={mentorExtraInfo}>
          <BookIcon />
          <p data-testid={mentor.courseCount}>
            {mentor.courseCount}
          </p>
        </div>
      </div>
    </div>
  );
};
export default MentorCard;
