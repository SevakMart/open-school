<<<<<<< HEAD
import { useEffect, useContext } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import { getMentorsList } from '../../../../redux/Slices/AllMentorsSlice';
=======
import { useEffect, useState, useContext } from 'react';
import { useLocation } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import mentorService from '../../../../services/mentorService';
import userService from '../../../../services/userService';
>>>>>>> main
import { userContext } from '../../../../contexts/Contexts';
import { MentorStateType } from '../../../../redux/Slices/AllMentorsFilterParamsSlice';
import MentorCard from '../../../../component/MentorProfile/MentorProfile';
import ContentRenderer from '../../../../component/ContentRenderer/ContentRenderer';
import styles from './Content.module.scss';

/* eslint-disable max-len */

const Content = () => {
<<<<<<< HEAD
  const { token } = useContext(userContext);
  const dispatch = useDispatch();
  const mentorsSendingParams = useSelector<RootState>((state) => state.allMentorsFilterParams) as MentorStateType;
  const mentorsList = useSelector<RootState>((state) => state.allMentorsList) as {entity:any[], isLoading:boolean, errorMessage:string};
  const { entity, isLoading, errorMessage } = mentorsList;
  const { mainContent } = styles;

  useEffect(() => {
    dispatch(getMentorsList({ params: mentorsSendingParams, token }));
  }, [mentorsSendingParams]);
=======
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const [mentorList, setMentorList] = useState<MentorType[]>([]);
  const [errorMessage, setErrorMessage] = useState('');
  const { token, id } = useContext(userContext);
  const { t } = useTranslation();
  const { mainContent } = styles;

  useEffect(() => {
    let requestMentors;
    if (params.get('searchedMentor')) {
      requestMentors = mentorService.searchMentorsByName(token, { name: params.get('searchedMentor'), page: 0, size: 100 });
    } else {
      requestMentors = mentorService.requestAllMentors(token, { page: 0, size: 100 });
    }
    /* eslint-disable-next-line max-len */
    Promise.all([requestMentors, mentorService.requestUserSavedMentors(id, token, { page: 0, size: 100 })])
      .then((combinedData) => {
        const requestedMentorsList = combinedData[0].content;
        const userSavedMentorList = combinedData[1].content;
        if (userSavedMentorList.length) {
        /* eslint-disable-next-line max-len */
          const finalMentorList = requestedMentorsList.reduce((acc:MentorType[], savedMentor:MentorType) => {
            const index = userSavedMentorList.findIndex(
              /* eslint-disable-next-line max-len */
              (mentor:MentorType) => mentor.name === savedMentor.name && mentor.surname === savedMentor.surname,
            );
            if (index !== -1) {
              acc.push({ ...savedMentor, isBookMarked: true });
              return acc;
            }

            acc.push({ ...savedMentor, isBookMarked: false });
            return acc;
          }, []);
          setMentorList([...finalMentorList]);
        } else {
          setMentorList(
            /* eslint-disable-next-line max-len */
            [...requestedMentorsList.map((mentor:MentorType) => ({ ...mentor, isBookMarked: false }))],
          );
        }
      }).catch(() => setErrorMessage(t('error')));
  }, [params.get('searchedMentor')]);
>>>>>>> main

  return (
    <div className={mainContent}>
      <ContentRenderer
        isLoading={isLoading}
        errorMessage={errorMessage}
        entity={entity}
        errorFieldClassName="allLearningPathErrorStyle"
        render={(entity) => (
          entity.map((mentor) => (
            <MentorCard
              key={`${mentor.name} ${mentor.surname}`}
              mentor={mentor}
              isHomepageNotSignedMentorCard={false}
            />
          ))
        )}
      />
    </div>
  );
};
export default Content;
