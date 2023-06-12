import { useEffect, useContext } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { RootState, DispatchType } from '../../../../redux/Store';
import { getMentorsList } from '../../../../redux/Slices/AllMentorsSlice';
import { userContext } from '../../../../contexts/Contexts';
import { MentorStateType } from '../../../../redux/Slices/AllMentorsFilterParamsSlice';
import MentorCard from '../../../../component/MentorProfile/MentorProfile';
import ContentRenderer from '../../../../component/ContentRenderer/ContentRenderer';
import { deleteUserSavedMentor } from '../../../../redux/Slices/DeleteSavedMentor';
import { getSavedMentors } from '../../../../redux/Slices/SavedMentorsSlice';
import { MentorType } from '../../../../types/MentorType';
import styles from './Content.module.scss';
import { saveUserMentor } from '../../../../redux/Slices/SaveUserMentorSlice';

const Content = () => {
  const { token, id: userId } = useContext(userContext);
  const dispatch = useDispatch<DispatchType>();
  const mentorsSendingParams = useSelector<RootState>((state) => state.allMentorsFilterParams) as MentorStateType;
  const mentorsList = useSelector<RootState>((state) => state.allMentorsList) as {entity:MentorType[], isLoading:boolean, errorMessage:string};
  const { entity, isLoading, errorMessage } = mentorsList;
  const { mainContent } = styles;

  const saveMentor = (mentorName:string, mentorId:number) => {
    dispatch(saveUserMentor({ userId, mentorId, token }));
  };

  const deleteMentor = (mentorName:string, mentorId:number) => {
    dispatch(deleteUserSavedMentor({ userId, mentorId, token }));
  };

  useEffect(() => {
    dispatch(getSavedMentors({ userId, token, params: mentorsSendingParams }));
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
