import { useEffect, useState, useContext } from 'react';
import mentorService from '../../../../services/mentorService';
import { userContext } from '../../../../contexts/Contexts';
import MentorCard from '../../../../component/MentorProfile/MentorProfile';
import { MentorType } from '../../../../types/MentorType';
import styles from './Content.module.scss';

const Content = () => {
  const [mentorList, setMentorList] = useState<MentorType[]>([]);
  const [errorMessage, setErrorMessage] = useState('');
  const { token, id } = useContext(userContext);
  const { mainContent } = styles;

  useEffect(() => {
    mentorService.requestAllMentors(token, { page: 0, size: 100 })
      .then((data) => setMentorList([...data.content]))
      .catch(() => setErrorMessage('For some reason the data could not be loaded, please try refreshing the page'));
  }, []);

  return (
    <div className={mainContent}>
      { errorMessage ? <h2>{errorMessage}</h2> : mentorList.length ? mentorList.map((mentor) => (
        <MentorCard
          key={mentor.emailPath}
          mentor={mentor}
        />
      )) : <h2>No data to display</h2>}
    </div>
  );
};
export default Content;
