import { useEffect, useState, useContext } from 'react';
import { useTranslation } from 'react-i18next';
import mentorService from '../../../../services/mentorService';
import userService from '../../../../services/userService';
import { userContext } from '../../../../contexts/Contexts';
import MentorCard from '../../../../component/MentorProfile/MentorProfile';
import { MentorType } from '../../../../types/MentorType';
import styles from './SavedMentors.module.scss';

const SavedMentors = () => {
  const [savedMentorList, setSavedMentorList] = useState<MentorType[]>([]);
  const [errorMessage, setErrorMessage] = useState('');
  const { token, id } = useContext(userContext);
  const { t } = useTranslation();
  const { mainContainer } = styles;

  const handleMentorDeletion = (mentorId:number) => {
    const mentorIndex = savedMentorList.findIndex((savedMentor) => savedMentor.id === mentorId);
    const mentorList = [...savedMentorList];
    mentorList.splice(mentorIndex, 1);
    userService.deleteUserSavedMentor(id, mentorId, token);
    setSavedMentorList(mentorList);
  };

  useEffect(() => {
    mentorService.requestUserSavedMentors(id, token, { page: 0, size: 100 })
      .then((data) => {
        /* eslint-disable-next-line max-len */
        setSavedMentorList([...data.content.map((savedMentor:MentorType) => ({ ...savedMentor, isBookMarked: true }))]);
      }).catch(() => setErrorMessage(t('Error Message')));
  }, []);

  return (
    <div className={mainContainer}>
      { /* eslint-disable-next-line max-len */
        errorMessage ? <h2>{errorMessage}</h2> : savedMentorList.length ? savedMentorList.map((savedMentor) => (
          <MentorCard
            key={savedMentor.id}
            mentor={savedMentor}
            deleteMentor={handleMentorDeletion}
          />
        )) : <h2>{t('Empty Data Error Message')}</h2>
        }
    </div>
  );
};
export default SavedMentors;
