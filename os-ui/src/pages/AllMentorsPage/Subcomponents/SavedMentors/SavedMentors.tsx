import { useEffect, useState, useContext } from 'react';
import mentorService from '../../../../services/mentorService';
import userService from '../../../../services/userService';
import { userContext } from '../../../../contexts/Contexts';
import MentorCard from '../../../../component/MentorProfile/MentorProfile';
import { MentorType } from '../../../../types/MentorType';
import styles from './SavedMentors.module.scss';

// type MentorListType=MentorType & {isBookmarked:boolean};

const SavedMentors = () => {
  const [savedMentorList, setSavedMentorList] = useState<MentorType[]>([]);
  const [errorMessage, setErrorMessage] = useState('');
  const { token, id } = useContext(userContext);
  const { mainContainer } = styles;

  const handleMentorDeletion = (mentorId:number) => {
    const mentorIndex = savedMentorList.findIndex((savedMentor) => savedMentor.id === mentorId);
    const mentorList = [...savedMentorList];
    mentorList.splice(mentorIndex, 1);
    userService.deleteUserSavedCourses(id, mentorId, token);
    setSavedMentorList(mentorList);
  };

  useEffect(() => {
    mentorService.requestUserSavedMentors(id, token, { page: 0, size: 100 })
      .then((data) => {
        /* eslint-disable-next-line max-len */
        setSavedMentorList([...data.content.map((savedMentor:MentorType) => ({ ...savedMentor, isBookMarked: true }))]);
      }).catch(() => setErrorMessage('For some reason the data could not be loaded, please try refreshing the page'));
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
        )) : <h2>No data to display</h2>
        }
    </div>
  );
};
export default SavedMentors;
