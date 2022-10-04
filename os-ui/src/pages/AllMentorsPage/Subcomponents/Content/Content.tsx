import { useEffect, useContext } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { RootState, DispatchType } from '../../../../redux/Store';
import { getMentorsList } from '../../../../redux/Slices/AllMentorsSlice';
import { userContext } from '../../../../contexts/Contexts';
import { MentorStateType } from '../../../../redux/Slices/AllMentorsFilterParamsSlice';
import MentorCard from '../../../../component/MentorProfile/MentorProfile';
import ContentRenderer from '../../../../component/ContentRenderer/ContentRenderer';
import { deleteUserSavedMentor } from '../../../../redux/Slices/DeleteSavedMentor';
import userService from '../../../../services/userService';
import { getSavedMentors } from '../../../../redux/Slices/SavedMentorsSlice';
import { MentorType } from '../../../../types/MentorType';
import styles from './Content.module.scss';

/* eslint-disable max-len */

const Content = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const params = new URLSearchParams(location.search);
  const { token, id: userId } = useContext(userContext);
  const dispatch = useDispatch<DispatchType>();
  const mentorsSendingParams = useSelector<RootState>((state) => state.allMentorsFilterParams) as MentorStateType;
  const mentorsList = useSelector<RootState>((state) => state.allMentorsList) as {entity:MentorType[], isLoading:boolean, errorMessage:string};
  const { entity, isLoading, errorMessage } = mentorsList;
  const { mainContent } = styles;

  const saveMentor = (mentorName:string, mentorId:number) => {
    userService.saveUserMentor(userId, mentorId, token);
    params.set(mentorName, String(mentorId));
    navigate(`${location.pathname}?${params}`);
  };

  const deleteMentor = (mentorName:string, mentorId:number) => {
    dispatch(deleteUserSavedMentor({ userId, mentorId, token }));
    params.delete(mentorName);
    navigate(`${location.pathname}?${params}`);
  };

  useEffect(() => {
    if (!params.toString()) {
      dispatch(getSavedMentors({ userId, token, params: mentorsSendingParams }))
        .unwrap()
        .then((userSavedMentorList:MentorType[]) => {
          for (const savedMentor of userSavedMentorList) {
            params.set(`${savedMentor.name} ${savedMentor.surname}`, String(savedMentor.id));
          }
          navigate(`${location.pathname}?${params}`);
        });
    }
  }, []);

  useEffect(() => {
    dispatch(getMentorsList({ params: mentorsSendingParams, token }));
  }, [mentorsSendingParams]);

  return (
    <div className={mainContent}>
      <ContentRenderer
        isLoading={isLoading}
        errorMessage={errorMessage}
        entity={entity}
        errorFieldClassName="allLearningPathErrorStyle"
        render={(entity) => (
          entity.map((mentor:MentorType) => (
            <MentorCard
              key={`${mentor.name} ${mentor.surname}`}
              mentor={mentor}
              isHomepageNotSignedMentorCard={false}
              saveMentor={saveMentor}
              deleteMentor={deleteMentor}
            />
          ))
        )}
      />
    </div>
  );
};
export default Content;
