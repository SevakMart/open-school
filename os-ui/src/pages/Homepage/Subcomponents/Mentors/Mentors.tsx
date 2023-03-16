import { useEffect, useState, useContext } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import MentorCard from '../../../../component/MentorProfile/MentorProfile';
import { getHomepageMentorsList } from '../../../../redux/Slices/HomepageMentorSlice';
import { MentorType } from '../../../../types/MentorType';
import { userContext } from '../../../../contexts/Contexts';
import ContentRenderer from '../../../../component/ContentRenderer/ContentRenderer';
import Title from '../Title/Title';
import MainBody from '../MainBody/MainBody';
import styles from './Mentors.module.scss';

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
  const { mentorMainContainer, gridContent } = styles;

  useEffect(() => {
    dispatch(getHomepageMentorsList({ page, token: token || '' }));
  }, [page]);

  return (
    <div className={mentorMainContainer}>
      <Title
        mainTitle={t('string.homePage.mentors.ourMentors')}
        subTitle={t('string.homePage.mentors.learnAboutContributors')}
      />
      <MainBody
        page={page}
        maxPage={totalPages}
        isMentor
        clickPrevious={() => setPage((prevPage) => prevPage - 1)}
        clickNext={() => setPage((prevPage) => prevPage + 1)}
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
