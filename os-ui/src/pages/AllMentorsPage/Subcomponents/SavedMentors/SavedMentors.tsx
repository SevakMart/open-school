import React, { useEffect, useState, useContext } from 'react';
import { useTranslation } from 'react-i18next';
import { useLocation } from 'react-router-dom';
import mentorService from '../../../../services/mentorService';
import { userContext } from '../../../../contexts/Contexts';
import { MentorType } from '../../../../types/MentorType';
import MentorCard from '../../../../component/MentorProfile/MentorProfile';
import styles from './SavedMentors.module.scss';
/* eslint-disable max-len */

const SavedMentors = () => {
  const { token, id: userId } = useContext(userContext);
  const [mentorsList, setMentorsList] = useState<MentorType[]>([]);
  const location = useLocation();
  const { t } = useTranslation();
  const NoDisplayedDataMessage = <h2 data-testid="emptyMessageHeader">{t('messages.noData.default')}</h2>;
  const { mainContainer } = styles;

  useEffect(() => {
    mentorService.requestUserSavedMentors(userId, token, { page: 0, size: 100 })
      .then((data) => setMentorsList([...data.content]));
  }, [location]);

  return (
    <div className={mainContainer}>

      {mentorsList.length > 0 ? mentorsList.map((savedMentor) => (
        <React.Fragment key={`${savedMentor.name} ${savedMentor.surname}`}>
          <MentorCard
            mentor={savedMentor}
          />
        </React.Fragment>
      )) : NoDisplayedDataMessage}
    </div>
  );
};
export default SavedMentors;
