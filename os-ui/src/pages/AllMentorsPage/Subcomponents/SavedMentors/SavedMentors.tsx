import React, { useEffect, useContext } from 'react';
import { useTranslation } from 'react-i18next';
import { useLocation } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import { getSavedMentors } from '../../../../redux/Slices/SavedMentorsSlice';
import Loader from '../../../../component/Loader/Loader';
import { ErrorField } from '../../../../component/ErrorField/ErrorField';
import { userContext } from '../../../../contexts/Contexts';
import { MentorType } from '../../../../types/MentorType';
import MentorCard from '../../../../component/MentorProfile/MentorProfile';
import styles from './SavedMentors.module.scss';
/* eslint-disable max-len */

const SavedMentors = () => {
  const { token, id: userId } = useContext(userContext);
  const dispatch = useDispatch();
  const savedMentorsState = useSelector<RootState>((state) => state.savedMentors)as {entity:MentorType[], isLoading:boolean, errorMessage:string};
  const { entity, isLoading, errorMessage } = savedMentorsState;
  const location = useLocation();
  const { t } = useTranslation();
  const NoDisplayedDataMessage = <h2 data-testid="emptyMessageHeader">{t('messages.noData.default')}</h2>;
  const { mainContainer } = styles;

  useEffect(() => {
    dispatch(getSavedMentors({ userId, token }));
  }, [location]);

  return (
    <div className={mainContainer}>
      {isLoading && <Loader />}
      {errorMessage !== '' && (
        <ErrorField.MainErrorField className={['allLearningPathErrorStyle']}>
          {errorMessage}
        </ErrorField.MainErrorField>
      )}
      {entity.length === 0 && !isLoading && errorMessage.length === 0 && NoDisplayedDataMessage }
      {entity.length > 0 && entity.map((savedMentor) => (
        <React.Fragment key={`${savedMentor.name} ${savedMentor.surname}`}>
          <MentorCard
            mentor={savedMentor}
            isHomepageNotSignedMentorCard={false}
          />
        </React.Fragment>
      )) }
    </div>
  );
};
export default SavedMentors;
