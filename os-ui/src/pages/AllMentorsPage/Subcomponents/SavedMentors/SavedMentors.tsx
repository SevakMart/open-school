import React, { useEffect, useContext } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { RootState, DispatchType } from '../../../../redux/Store';
import { getSavedMentors } from '../../../../redux/Slices/SavedMentorsSlice';
import { userContext } from '../../../../contexts/Contexts';
import { MentorType } from '../../../../types/MentorType';
import MentorCard from '../../../../component/MentorProfile/MentorProfile';
import { MentorStateType } from '../../../../redux/Slices/AllMentorsFilterParamsSlice';
import { deleteUserSavedMentor } from '../../../../redux/Slices/DeleteSavedMentor';
import ContentRenderer from '../../../../component/ContentRenderer/ContentRenderer';
import styles from './SavedMentors.module.scss';
/* eslint-disable max-len */

const SavedMentors = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const params = new URLSearchParams(location.search);
  const { token, id: userId } = useContext(userContext);
  const dispatch = useDispatch<DispatchType>();
  const mentorsSendingParams = useSelector<RootState>((state) => state.allMentorsFilterParams) as MentorStateType;
  const savedMentorsState = useSelector<RootState>((state) => state.savedMentors)as {entity:MentorType[], isLoading:boolean, errorMessage:string};
  const deletedSavedMentorState = useSelector<RootState>((state) => state.deleteUserSavedMentor) as {entity:string, isLoading:boolean, errorMessage:string};
  const { entity, isLoading, errorMessage } = savedMentorsState;
  const { mainContainer } = styles;

  const deleteMentor = (mentorName:string, mentorId:number) => {
    dispatch(deleteUserSavedMentor({ userId, mentorId, token }));
    params.delete(mentorName);
    navigate(`${location.pathname}?${params}`);
  };

  useEffect(() => {
    dispatch(getSavedMentors({ userId, token, params: mentorsSendingParams }));
  }, [deletedSavedMentorState, mentorsSendingParams]);

  return (
    <div className={mainContainer}>
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
              deleteMentor={deleteMentor}
            />
          ))
        )}
      />
    </div>
  );
};
export default SavedMentors;
