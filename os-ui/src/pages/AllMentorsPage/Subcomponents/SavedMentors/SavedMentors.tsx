import React, { useEffect, useContext } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import { getSavedMentors } from '../../../../redux/Slices/SavedMentorsSlice';
import { userContext } from '../../../../contexts/Contexts';
import { MentorType } from '../../../../types/MentorType';
import MentorCard from '../../../../component/MentorProfile/MentorProfile';
import { MentorStateType } from '../../../../redux/Slices/AllMentorsFilterParamsSlice';
import ContentRenderer from '../../../../component/ContentRenderer/ContentRenderer';
import styles from './SavedMentors.module.scss';
/* eslint-disable max-len */

const SavedMentors = () => {
  const { token, id: userId } = useContext(userContext);
  const dispatch = useDispatch();
  const mentorsSendingParams = useSelector<RootState>((state) => state.allMentorsFilterParams) as MentorStateType;
  const savedMentorsState = useSelector<RootState>((state) => state.savedMentors)as {entity:MentorType[], isLoading:boolean, errorMessage:string};
  const deletedSavedMentorState = useSelector<RootState>((state) => state.deleteUserSavedMentor) as {entity:MentorType[], isLoading:boolean, errorMessage:string};
  const { entity, isLoading, errorMessage } = savedMentorsState;
  const { mainContainer } = styles;

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
            />
          ))
        )}
      />
    </div>
  );
};
export default SavedMentors;
