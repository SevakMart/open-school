import { useEffect, useContext } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import { getMentorsList } from '../../../../redux/Slices/AllMentorsSlice';
import Loader from '../../../../component/Loader/Loader';
import { ErrorField } from '../../../../component/ErrorField/ErrorField';
import { userContext } from '../../../../contexts/Contexts';
import { MentorStateType } from '../../../../redux/Slices/AllMentorsFilterParamsSlice';
import MentorCard from '../../../../component/MentorProfile/MentorProfile';
import styles from './Content.module.scss';
/* eslint-disable max-len */
const Content = () => {
  const { token } = useContext(userContext);
  const dispatch = useDispatch();
  const mentorsSendingParams = useSelector<RootState>((state) => state.allMentorsFilterParams) as MentorStateType;
  const mentorsList = useSelector<RootState>((state) => state.allMentorsList) as {entity:any[], isLoading:boolean, errorMessage:string};
  const { entity, isLoading, errorMessage } = mentorsList;
  const { t } = useTranslation();
  const { mainContent } = styles;
  const NoDisplayedDataMessage = <h2 data-testid="emptyMessageHeader">{t('messages.noData.default')}</h2>;

  useEffect(() => {
    dispatch(getMentorsList({ params: mentorsSendingParams, token }));
  }, [mentorsSendingParams]);

  return (
    <div className={mainContent}>
      {isLoading && <Loader />}
      {errorMessage !== '' && (
        <ErrorField.MainErrorField className={['allLearningPathErrorStyle']}>
          {errorMessage}
        </ErrorField.MainErrorField>
      )}
      {entity.length === 0 && !isLoading && NoDisplayedDataMessage }
      {entity.length > 0 && entity.map((mentor) => (
        <MentorCard
          key={`${mentor.name} ${mentor.surname}`}
          mentor={mentor}
        />
      ))}
    </div>
  );
};
export default Content;
