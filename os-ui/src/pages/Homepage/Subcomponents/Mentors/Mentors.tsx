import { useEffect, useState, useContext } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import MentorCard from '../../../../component/MentorProfile/MentorProfile';
import { getHomepageMentorsList } from '../../../../redux/Slices/HomepageMentorSlice';
import { MentorType } from '../../../../types/MentorType';
import { userContext } from '../../../../contexts/Contexts';
import ContentRenderer from '../../../../component/ContentRenderer/ContentRenderer';
import MainBody from '../MainBody/MainBody';
import styles from './Mentors.module.scss';
import RightArrowIcon from '../../../../icons/RightArrow';
import LeftArrowIcon from '../../../../icons/LeftArrow';

/* eslint-disable max-len */

const HomepageMentors = () => {
  const { token } = useContext(userContext);
  const dispatch = useDispatch();
  const homepageMentorListState = useSelector<RootState>((state) => state.homepageMentorList)as {entity:MentorType[], isLoading:boolean, errorMessage:string, totalPages:number};
  const {
    entity, isLoading, errorMessage, totalPages,
  } = homepageMentorListState;
  const { t } = useTranslation();
  const [page, setPage] = useState(0);
  const {
    mentorMainContainer, gridContent, MentorTitle, MentorSubTitle, icon,
  } = styles;

  useEffect(() => {
    dispatch(getHomepageMentorsList({ page, token: token || '' }));
  }, [page]);

  return (
    <div className={mentorMainContainer}>
      <p className={MentorTitle}>
        {t('string.homePage.mentors.ourMentors')}
      </p>
      <p className={MentorSubTitle}>
        {t('string.homePage.mentors.learnAboutContributors')}
      </p>
      {page < totalPages
        && (
        <div className={icon}>
          <RightArrowIcon testId="categoryRightArrow" handleArrowClick={() => setPage((prevPage) => prevPage + 1)} />
        </div>
        )}
      {page > 0 && (
      <div className={icon}>
        <LeftArrowIcon testId="categoryLeftArrow" handleArrowClick={() => setPage((prevPage) => prevPage - 1)} />
      </div>
      )}
      <MainBody
        isMentor
      >
        <div className={gridContent}>
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
                  isHomepageNotSignedMentorCard
                />
              ))
            )}
          />
        </div>
      </MainBody>
    </div>
  );
};
export default HomepageMentors;
