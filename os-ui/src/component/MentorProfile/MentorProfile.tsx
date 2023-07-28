import { useTranslation } from 'react-i18next';
import { MentorType } from '../../types/MentorType';
import EmailIcon from '../../icons/Email';
import LinkedinIcon from '../../icons/Linkedin';
import BookmarkIcon from '../../icons/Bookmark';
import BookIcon from '../../icons/Book';
import Separator from '../../assets/svg/Separator.svg';
import styles from './MentorProfile.module.scss';
import mentor_avatarka from '../../assets/svg/mentor_avatarka.svg';

/* eslint-disable max-len */

const MentorCard = ({
  mentor, isHomepageNotSignedMentorCard, saveMentor, deleteMentor,
}:
  {mentor:MentorType, isHomepageNotSignedMentorCard:boolean, saveMentor?:(mentorName:string, mentorId:number)=>void,
    deleteMentor?:(mentorName:string, mentorId:number)=>void, }) => {
  const {
    mainContainer, headerContainer, MailLinkedinIconsContainer, bodyContainer, mentorInfo,
    countBeforeContent, mentorCourseProperty, mentorAvatar, mentorInfoContainer, mentorExtraInfo,
    professionName, companyName, mentorCourseCount, countBeforeContentBox,
  } = styles;

  const { t } = useTranslation();

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
            <img src={mentor_avatarka} alt="mentor_avatar" />
          </div>
          <div className={mentorInfo}>
            <h3 data-testid={`${mentor.name} ${mentor.surname}`}>
              {mentor.name}
              {' '}
              {mentor.surname}
            </h3>
            <p className={professionName} data-testid={mentor.professionName}>{mentor.professionName}</p>
            <p className={companyName} data-testid={mentor.companyName}>{mentor.companyName}</p>
          </div>
        </div>
        <div className={mentorExtraInfo}>
          <div className={countBeforeContentBox} data-testid={mentor.courseCount}>
            <BookIcon />
            <p className={countBeforeContent}>{t('Total Courses:')}</p>
            <p className={mentorCourseCount}>{mentor.courseCount}</p>
            <button type="button" className={mentorCourseProperty}>Creative</button>
          </div>
        </div>
      </div>
    </div>
  );
};
export default MentorCard;
