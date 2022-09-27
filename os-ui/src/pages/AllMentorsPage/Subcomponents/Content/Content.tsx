import { useEffect, useContext } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import { getMentorsList } from '../../../../redux/Slices/AllMentorsSlice';
import { userContext } from '../../../../contexts/Contexts';
import { MentorStateType } from '../../../../redux/Slices/AllMentorsFilterParamsSlice';
import MentorCard from '../../../../component/MentorProfile/MentorProfile';
import ContentRenderer from '../../../../component/ContentRenderer/ContentRenderer';
import { MentorType } from '../../../../types/MentorType';
import styles from './Content.module.scss';

/* eslint-disable max-len */

const Content = () => {
  const { token } = useContext(userContext);
  const dispatch = useDispatch();
  const mentorsSendingParams = useSelector<RootState>((state) => state.allMentorsFilterParams) as MentorStateType;
  const mentorsList = useSelector<RootState>((state) => state.allMentorsList) as {entity:MentorType[], isLoading:boolean, errorMessage:string};
  const { entity, isLoading, errorMessage } = mentorsList;
  const { mainContent } = styles;

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
