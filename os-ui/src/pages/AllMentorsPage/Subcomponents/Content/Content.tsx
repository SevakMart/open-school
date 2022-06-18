import { useEffect, useState, useContext } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import mentorService from '../../../../services/mentorService';
import userService from '../../../../services/userService';
import { userContext } from '../../../../contexts/Contexts';
import MentorCard from '../../../../component/MentorProfile/MentorProfile';
import { MentorType } from '../../../../types/MentorType';
import styles from './Content.module.scss';

type MentorListType=MentorType & {isBookmarked:boolean}

const Content = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const params = new URLSearchParams(location.search);
  const [mentorList, setMentorList] = useState<MentorListType[]>([]);
  const [errorMessage, setErrorMessage] = useState('');
  const { token, id } = useContext(userContext);
  const { mainContent } = styles;

  useEffect(() => {
    let requestMentors;
    if (params.get('searchedMentor')) {
      requestMentors = mentorService.searchMentorsByName(token, { name: params.get('searchedMentor'), page: 0, size: 100 });
    } else {
      requestMentors = mentorService.requestAllMentors(token, { page: 0, size: 100 });
      navigate('/mentors');
    }
    /* eslint-disable-next-line max-len */
    Promise.all([requestMentors, mentorService.requestUserSavedMentors(id, token, { page: 0, size: 100 })])
      .then((combinedData) => {
        const requestedMentorsList = combinedData[0].content;
        const userSavedMentorList = combinedData[1].content;
        if (userSavedMentorList.length) {
        /* eslint-disable-next-line max-len */
          const finalMentorList = requestedMentorsList.reduce((acc:MentorListType[], savedMentor:MentorType) => {
            const index = userSavedMentorList.findIndex(
              /* eslint-disable-next-line max-len */
              (mentor:MentorType) => mentor.name === savedMentor.name && mentor.surname === savedMentor.surname,
            );
            if (index !== -1) {
              acc.push({ ...savedMentor, isBookmarked: true });
              return acc;
            }

            acc.push({ ...savedMentor, isBookmarked: false });
            return acc;
          }, []);
          setMentorList([...finalMentorList]);
        } else {
          setMentorList(
            /* eslint-disable-next-line max-len */
            [...requestedMentorsList.map((mentor:MentorType) => ({ ...mentor, isBookmarked: false }))],
          );
        }
      }).catch(() => setErrorMessage('For some reason the data could not be loaded, please try refreshing the page'));
  }, [params.get('searchedMentor')]);
  // For temporary solution I will add index as mentor id.
  // mentorId+1 is temporary because it is using the index of map to show the mentor id
  return (
    <div className={mainContent}>
      { errorMessage ? <h2>{errorMessage}</h2>
        : mentorList.length ? mentorList.map((mentor, index) => (
          <MentorCard
            key={index}
            mentor={mentor}
            id={index}
            isBookMarked={mentor.isBookmarked}
            saveMentor={(mentorId) => userService.saveUserMentor(id, mentorId + 1, token)}
          />
        )) : <h2>No data to display</h2>}
    </div>
  );
};
export default Content;
